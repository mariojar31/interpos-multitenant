package com.interfija.masterposmultitenant.dtos;

public record TenantRegistrationDto(
        String name,
        String dbName,
        String dbUsername,
        String dbPassword
) {}