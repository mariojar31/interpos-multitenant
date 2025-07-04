package com.interfija.masterposmultitenant.controller;

import com.interfija.masterposmultitenant.dto.TenantDTO;
import com.interfija.masterposmultitenant.dto.TenantRegistrationDto;
import com.interfija.masterposmultitenant.services.TenantService;
import jakarta.validation.Valid;
import jakarta.xml.bind.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping("/register")
    public ResponseEntity<TenantDTO> registerTenant(
            @Valid @RequestBody TenantRegistrationDto registrationDto) throws ValidationException {
        TenantDTO tenantDTO = tenantService.registerNewTenant(registrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(tenantDTO);
    }

    @GetMapping("/{tenantId}")
    public ResponseEntity<TenantDTO> getTenant(@PathVariable String tenantId) {
        TenantDTO tenantDTO = tenantService.getTenantById(tenantId);
        return ResponseEntity.ok(tenantDTO);
    }

    @GetMapping
    public ResponseEntity<List<TenantDTO>> getAllTenants() {
        List<TenantDTO> tenants = tenantService.getAllTenants();
        return ResponseEntity.ok(tenants);
    }

    @PutMapping("/{tenantId}/activate")
    public ResponseEntity<Void> activateTenant(@PathVariable String tenantId) {
        tenantService.activateTenant(tenantId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{tenantId}/deactivate")
    public ResponseEntity<Void> deactivateTenant(@PathVariable String tenantId) {
        tenantService.deactivateTenant(tenantId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{tenantId}")
    public ResponseEntity<Void> deleteTenant(@PathVariable String tenantId) {
        tenantService.deleteTenant(tenantId);
        return ResponseEntity.noContent().build();
    }
}
