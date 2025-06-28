package com.interfija.masterposmultitenant.services;

import com.interfija.masterposmultitenant.dtos.TenantDTO;
import com.interfija.masterposmultitenant.dtos.TenantRegistrationDto;
import com.interfija.masterposmultitenant.exception.TenantNotFoundException;
import com.interfija.masterposmultitenant.model.Tenant;
import com.interfija.masterposmultitenant.repository.TenantRepository;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.xml.bind.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TenantService {

    private final TenantRepository tenantRepository;
    private final PasswordEncoder passwordEncoder;
    private final TenantDataSourceService tenantDataSourceService;

    public void activateTenant(String tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new TenantNotFoundException(tenantId));
        tenantDataSourceService.loadDataSource(tenant);
    }

    public TenantDTO registerNewTenant(TenantRegistrationDto registrationDto) throws ValidationException {
        validateTenantRegistration(registrationDto);

        Tenant tenant = buildTenantFromRegistration(registrationDto);
        verifyDatabaseConnection(tenant);

        Tenant savedTenant = tenantRepository.save(tenant);
        tenantDataSourceService.loadDataSource(savedTenant);

        return TenantDTO.fromEntity(savedTenant);
    }

    private void validateTenantRegistration(TenantRegistrationDto dto) throws ValidationException {
        if (tenantRepository.existsByName(dto.name())) {
            throw new ValidationException("Ya existe un tenant con ese nombre");
        }
        if (tenantRepository.existsByDbName(dto.dbName())) {
            throw new ValidationException("Ya existe un tenant con ese nombre de base de datos");
        }
    }

    private Tenant buildTenantFromRegistration(TenantRegistrationDto dto) {
        return Tenant.builder()
                .id(UUID.randomUUID().toString())
                .name(dto.name())
                .dbName(dto.dbName())
                .dbUrl(buildDbUrl(dto))
                .dbUsername(dto.dbUsername()) // Se actualizará en verifyDatabaseConnection
                .dbPassword(dto.dbPassword()) // Se actualizará en verifyDatabaseConnection
                .active(true)
                .build();
    }

    private String buildDbUrl(TenantRegistrationDto dto) {
        return String.format("jdbc:mysql://localhost:3306/%s?" +
                "useSSL=false&" +
                "serverTimezone=UTC&" +
                "allowPublicKeyRetrieval=true&" +
                "useServerPrepStmts=false", dto.dbName());
    }

    private void verifyDatabaseConnection(Tenant tenant) throws ValidationException {
        try {
            // 1. Conexión administrativa - usando HikariConfig para mejor control
            HikariConfig adminConfig = new HikariConfig();
            adminConfig.setJdbcUrl("jdbc:mysql://localhost:3306/mysql?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
            adminConfig.setUsername("admin_db");
            adminConfig.setPassword("Qwerty123*");
            adminConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
            adminConfig.setAutoCommit(false); // Crítico
            adminConfig.setMaximumPoolSize(5);
            adminConfig.setConnectionTimeout(30000);

            HikariDataSource adminDataSource = new HikariDataSource(adminConfig);

            String tenantUser = "tenant_" + tenant.getDbName();
            String tenantPassword = generateSecurePassword();

            try (Connection adminConn = adminDataSource.getConnection();
                 Statement stmt = adminConn.createStatement()) {

                // 2. Crear BD
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + tenant.getDbName());

                // 3. Crear usuario con autenticación nativa
                stmt.executeUpdate(
                        "CREATE USER IF NOT EXISTS '" + tenantUser + "'@'%' IDENTIFIED WITH caching_sha2_password BY '" + tenantPassword + "'"
                );
                stmt.executeUpdate(
                        "CREATE USER IF NOT EXISTS '" + tenantUser + "'@'localhost' IDENTIFIED WITH caching_sha2_password BY '" + tenantPassword + "'"
                );

                // 4. Otorgar permisos
                stmt.executeUpdate(
                        "GRANT ALL PRIVILEGES ON " + tenant.getDbName() + ".* TO '" + tenantUser + "'@'%'"
                );
                stmt.executeUpdate(
                        "GRANT ALL PRIVILEGES ON " + tenant.getDbName() + ".* TO '" + tenantUser + "'@'localhost'"
                );

                // 5. Verificar conexión con las credenciales recién creadas
                String tenantJdbcUrl = String.format(
                        "jdbc:mysql://localhost:3306/%s?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&useServerPrepStmts=false",
                        tenant.getDbName()
                );

                // Usar HikariConfig también para el tenant
                HikariConfig tenantConfig = new HikariConfig();
                tenantConfig.setJdbcUrl(tenantJdbcUrl);
                tenantConfig.setUsername(tenantUser);
                tenantConfig.setPassword(tenantPassword);
                tenantConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
                tenantConfig.setAutoCommit(false); // Crítico
                tenantConfig.setMaximumPoolSize(5);
                tenantConfig.setConnectionTimeout(30000);

                HikariDataSource tenantDataSource = new HikariDataSource(tenantConfig);

                tenant.setDbUrl(tenantJdbcUrl);

                try (Connection tenantConn = tenantDataSource.getConnection()) {
                    if (!tenantConn.isValid(5)) {
                        throw new ValidationException("Conexión no válida");
                    }

                    // 6. Actualizar el tenant con las credenciales
                    tenant.setDbUsername(tenantUser);
                    tenant.setDbPassword(tenantPassword);
                } finally {
                    tenantDataSource.close();
                }
            } finally {
                adminDataSource.close();
            }
        } catch (SQLException e) {
            throw new ValidationException("Error configurando tenant: " + e.getMessage());
        }
    }

    private String generateSecurePassword() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }

    public void deactivateTenant(String tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new TenantNotFoundException(tenantId));
        tenant.setActive(false);
        tenantRepository.save(tenant);
    }

    public void deleteTenant(String tenantId) {
        if (!tenantRepository.existsById(tenantId)) {
            throw new TenantNotFoundException(tenantId);
        }
        tenantRepository.deleteById(tenantId);
    }

    public TenantDTO getTenantById(String tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new TenantNotFoundException(tenantId));
        return TenantDTO.fromEntity(tenant);
    }

    public List<TenantDTO> getAllTenants() {
        return tenantRepository.findAll().stream()
                .map(TenantDTO::fromEntity)
                .toList();
    }

    // Agregar estos métodos a tu TenantService existente:

    /**
     * Verifica si existe un tenant con el ID especificado
     * @param tenantId ID del tenant a verificar
     * @return true si el tenant existe, false en caso contrario
     */
    public boolean existsByTenantId(String tenantId) {
        return tenantRepository.existsById(tenantId);
    }

    /**
     * Verifica si existe un tenant con el ID especificado y está activo
     * @param tenantId ID del tenant a verificar
     * @return true si el tenant existe y está activo, false en caso contrario
     */
    public boolean existsByTenantIdAndActive(String tenantId) {
        return tenantRepository.existsByIdAndActiveTrue(tenantId);
    }

    /**
     * Obtiene un tenant activo por su ID
     * @param tenantId ID del tenant
     * @return Tenant activo
     * @throws TenantNotFoundException si el tenant no existe o no está activo
     */
    public Tenant getActiveTenantById(String tenantId) {
        return tenantRepository.findByIdAndActiveTrue(tenantId)
                .orElseThrow(() -> new TenantNotFoundException("Tenant activo no encontrado: " + tenantId));
    }

    /**
     * Verifica si un tenant existe y está activo
     * @param tenantId ID del tenant
     * @return true si existe y está activo
     */
    public boolean isActiveTenant(String tenantId) {
        return tenantRepository.findById(tenantId)
                .map(Tenant::isActive)
                .orElse(false);
    }

}