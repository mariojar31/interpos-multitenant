package com.interfija.masterposmultitenant.config;

import com.interfija.masterposmultitenant.services.TenantDataSourceService;
import com.interfija.masterposmultitenant.tenant.ClientDataSourceRouter;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    private String masterUrl;

    @Value("${spring.datasource.username}")
    private String masterUsername;

    @Value("${spring.datasource.password}")
    private String masterPassword;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean(name = "dataSource")
    @Primary
    public ClientDataSourceRouter dataSourceRouter() {
        ClientDataSourceRouter routingDataSource = new ClientDataSourceRouter();

        // Configurar correctamente el DataSource master
        HikariDataSource masterDataSource = createHikariDataSource(
                masterUrl, masterUsername, masterPassword, "master-pool"
        );

        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("master", masterDataSource);

        routingDataSource.initialize(targetDataSources);
        routingDataSource.addDataSource("master", masterDataSource);

        // Esto es necesario para AbstractRoutingDataSource
        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.setDefaultTargetDataSource(masterDataSource);

        return routingDataSource;
    }

    private HikariDataSource createHikariDataSource(String url, String username, String password, String poolName) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driverClassName);

        // Configuración crítica para transacciones
        config.setAutoCommit(false);
        config.setPoolName(poolName);
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(3);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(30000);
        config.setMaxLifetime(2000000);

        return new HikariDataSource(config);
    }
}