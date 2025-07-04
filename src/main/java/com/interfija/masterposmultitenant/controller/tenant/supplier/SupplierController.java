package com.interfija.masterposmultitenant.controller.tenant.supplier;

import com.interfija.masterposmultitenant.dto.supplier.SupplierDTO;
import com.interfija.masterposmultitenant.services.supplier.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller REST para gestionar proveedores.
 * Autor: Steven Arzuza
 */
@RestController
@RequestMapping("/api/tenant/supplier")
public class SupplierController {

    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    /**
     * Obtiene un proveedor por su ID.
     */
    @GetMapping("/{idSupplier}")
    public ResponseEntity<Optional<SupplierDTO>> getSupplier(@PathVariable Long idSupplier) {
        Optional<SupplierDTO> supplier = supplierService.getSupplier(idSupplier);
        return ResponseEntity.ok(supplier);
    }

    /**
     * Obtiene lista básica de proveedores por visibilidad.
     */
    @GetMapping("/basic-list")
    public ResponseEntity<List<SupplierDTO>> getSupplierBasicList(
            @RequestParam(required = false) Long companyId,
            @RequestParam boolean visible) {
        List<SupplierDTO> list = (companyId == null)
                ? supplierService.getSupplierBasicList(visible)
                : supplierService.getSupplierBasicList(companyId, visible);
        return ResponseEntity.ok(list);
    }

    /**
     * Obtiene lista detallada (resumen) de proveedores por visibilidad.
     */
    @GetMapping("/summary-list")
    public ResponseEntity<List<SupplierDTO>> getSupplierSummaryList(
            @RequestParam(required = false) Long companyId,
            @RequestParam boolean visible) {
        List<SupplierDTO> list = (companyId == null)
                ? supplierService.getSupplierSummaryList(visible)
                : supplierService.getSupplierSummaryList(companyId, visible);
        return ResponseEntity.ok(list);
    }

    /**
     * Guarda (inserta o actualiza) un proveedor.
     */
    @PostMapping("/save")
    public ResponseEntity<Boolean> saveSupplier(@RequestBody SupplierDTO supplierDTO) {
        boolean success = supplierService.saveSupplier(supplierDTO);
        return ResponseEntity.ok(success);
    }

    /**
     * Elimina un proveedor.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteSupplier(@RequestBody SupplierDTO supplierDTO) {
        boolean success = supplierService.deleteSupplier(supplierDTO);
        return ResponseEntity.ok(success);
    }
}
