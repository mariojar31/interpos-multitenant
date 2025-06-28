package com.interfija.masterposmultitenant.tenant;

import com.interfija.masterposmultitenant.context.TenantContext;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientDataSourceRouter extends AbstractRoutingDataSource {

    private final ConcurrentHashMap<Object, Object> dataSources = new ConcurrentHashMap<>();

    @Override
    protected Object determineCurrentLookupKey() {
        String tenantId = TenantContext.getCurrentTenant();
        return tenantId != null ? tenantId : "master";
    }

    @Override
    protected DataSource determineTargetDataSource() {
        Object lookupKey = determineCurrentLookupKey();
        DataSource dataSource = (DataSource) dataSources.get(lookupKey);

        if (dataSource == null) {
            dataSource = (DataSource) dataSources.get("master");
            if (dataSource == null) {
                throw new IllegalStateException("No se encontró DataSource para: " + lookupKey);
            }
        }

        return dataSource;
    }

    public void addDataSource(String tenantId, DataSource dataSource) {
        this.dataSources.put(tenantId, dataSource);
        // Actualizar también el mapa de AbstractRoutingDataSource
        setTargetDataSources(Map.copyOf(this.dataSources));
        afterPropertiesSet();
    }

    public void initialize(Map<Object, Object> initialDataSources) {
        this.dataSources.putAll(initialDataSources);
    }

    public boolean hasDataSource(String tenantId) {
        return this.dataSources.containsKey(tenantId);
    }
}