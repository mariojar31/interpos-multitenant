package com.interfija.masterposmultitenant.services;

import com.interfija.masterposmultitenant.model.Tenant;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.interfija.masterposmultitenant.tenant.ClientDataSourceRouter;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TenantDataSourceService {

    private final ClientDataSourceRouter dataSourceRouter;

    public void loadDataSource(Tenant tenant) {
        DataSource dataSource = buildDataSource(tenant);
        dataSourceRouter.addDataSource(tenant.getId(), dataSource);
    }

    private DataSource buildDataSource(Tenant tenant) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(tenant.getDbUrl());
        config.setUsername(tenant.getDbUsername());
        config.setPassword(tenant.getDbPassword());
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        // Configuración crítica - igual que el master
        config.setAutoCommit(false);
        config.setPoolName("tenant-" + tenant.getId() + "-pool");
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(3);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(30000);
        config.setMaxLifetime(2000000);

        return new HikariDataSource(config);
    }

    public void migrateTenantDatabase(Tenant tenant) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(tenant.getDbUrl());
        config.setUsername(tenant.getDbUsername());
        config.setPassword(tenant.getDbPassword());
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        HikariDataSource tenantDataSource = new HikariDataSource(config);

        try {
            Flyway flyway = Flyway.configure()
                    .dataSource(tenantDataSource)
                    .locations("classpath:db/migration/tenants") // ruta a migraciones para tenants
                    .baselineOnMigrate(true)
                    .load();

            flyway.migrate();

        } finally {
            tenantDataSource.close();
        }
    }
}