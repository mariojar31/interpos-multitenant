package com.interfija.masterposmultitenant.dto;

import com.interfija.masterposmultitenant.entities.Tenant;

import java.time.LocalDateTime;

public record TenantDTO(
        String id,
        String name,
        String dbName,
        boolean active,
        LocalDateTime createdAt
) {
    public static TenantDTO fromEntity(Tenant tenant) {
        return new TenantDTO(
                tenant.getId(),
                tenant.getName(),
                tenant.getDbName(),
                tenant.isActive(),
                tenant.getCreatedAt()
        );
    }
}