package com.interfija.masterposmultitenant.dto;

public record TenantRegistrationDto(
        String name,
        String dbName,
        String dbUsername,
        String dbPassword
) {}