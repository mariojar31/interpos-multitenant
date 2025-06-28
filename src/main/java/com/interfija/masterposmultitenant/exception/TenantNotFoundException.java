package com.interfija.masterposmultitenant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TenantNotFoundException extends RuntimeException {

    public TenantNotFoundException(String message) {
        super(message);
    }

    public TenantNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    // Versión con formato
    public TenantNotFoundException(String tenantId, String resource) {
        super(String.format("Tenant con ID %s no encontrado para %s", tenantId, resource));
    }
}