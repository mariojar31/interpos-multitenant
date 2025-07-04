package com.interfija.masterposmultitenant.controller.tenant.inventory;

import com.interfija.masterposmultitenant.dto.inventory.InventorySummaryDTO;
import com.interfija.masterposmultitenant.services.inventory.InventoryReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar los reportes de inventario.
 * Expone endpoints relacionados con el resumen de productos en inventario.
 */
@RestController
@RequestMapping("/api/tenant/inventory-report")
public class InventoryReportController {

    private final InventoryReportService inventoryReportService;

    @Autowired
    public InventoryReportController(InventoryReportService inventoryReportService) {
        this.inventoryReportService = inventoryReportService;
    }

    /**
     * Obtiene el resumen del inventario de productos para una sucursal específica.
     *
     * @param branchId ID de la sucursal
     * @return Lista de productos con su resumen de inventario
     */
    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<InventorySummaryDTO>> getInventoryByBranch(@PathVariable Long branchId) {
        List<InventorySummaryDTO> inventory = inventoryReportService.getInventoryProduct(branchId);
        return ResponseEntity.ok(inventory);
    }
}
