package com.interfija.masterposmultitenant.controller.tenant.sale;

import com.interfija.masterposmultitenant.dto.invoice.InvoiceDTO;
import com.interfija.masterposmultitenant.dto.product.ProductBranchDTO;
import com.interfija.masterposmultitenant.dto.sale.SalePendingDTO;
import com.interfija.masterposmultitenant.services.sale.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller REST que expone los endpoints para gestionar ventas y ventas pendientes.
 * Autor: Steven Arzuza
 */
@RestController
@RequestMapping("/api/tenant/sales")
public class SalesController {

    private final SalesService salesService;

    @Autowired
    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    /**
     * Guarda una venta junto con actualización de stock.
     */
    @PostMapping("/save-sale")
    public ResponseEntity<Boolean> saveSale(@RequestBody SaveSaleRequest request) {
        boolean success = salesService.saveSale(request.invoiceDTO(), request.productStocksList());
        return ResponseEntity.ok(success);
    }

    /**
     * Elimina una factura.
     */
    @DeleteMapping("/delete-invoice")
    public ResponseEntity<Boolean> deleteInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        boolean success = salesService.deleteInvoice(invoiceDTO);
        return ResponseEntity.ok(success);
    }

    /**
     * Obtiene el conteo de ventas pendientes para una sucursal.
     */
    @GetMapping("/pending/count/{branchId}")
    public ResponseEntity<Integer> getSalePendingCount(@PathVariable long branchId) {
        int count = salesService.getSalePendingCount(branchId);
        return ResponseEntity.ok(count);
    }

    /**
     * Obtiene la lista de ventas pendientes por sucursal.
     */
    @GetMapping("/pending/list/{branchId}")
    public ResponseEntity<List<SalePendingDTO>> getSalesPendingList(@PathVariable long branchId) {
        List<SalePendingDTO> salesPending = salesService.getSalesPendingList(branchId);
        return ResponseEntity.ok(salesPending);
    }

    /**
     * Obtiene la lista de ventas pendientes agrupadas por mesa.
     */
    @GetMapping("/pending/by-tables/{branchId}")
    public ResponseEntity<List<SalePendingDTO>> getSalesPendingByTablesList(@PathVariable long branchId) {
        List<SalePendingDTO> salesPending = salesService.getSalesPendingByTablesList(branchId);
        return ResponseEntity.ok(salesPending);
    }

    /**
     * Obtiene la factura pendiente en formato JSON por su ID.
     */
    @GetMapping("/pending/invoice/{idSalePending}")
    public ResponseEntity<Optional<String>> getInvoiceSalePending(@PathVariable String idSalePending) {
        Optional<String> invoiceJson = salesService.getInvoiceSalePending(idSalePending);
        return ResponseEntity.ok(invoiceJson);
    }

    /**
     * Guarda o actualiza una venta pendiente.
     */
    @PostMapping("/pending/save")
    public ResponseEntity<Boolean> saveSalePending(@RequestBody SalePendingDTO salePendingDTO) {
        boolean success = salesService.saveSalePending(salePendingDTO);
        return ResponseEntity.ok(success);
    }

    /**
     * Elimina una venta pendiente.
     */
    @DeleteMapping("/pending/delete")
    public ResponseEntity<Boolean> deleteSalePending(@RequestBody SalePendingDTO salePendingDTO) {
        boolean success = salesService.deleteSalePending(salePendingDTO);
        return ResponseEntity.ok(success);
    }

    /**
     * Clase interna para deserializar la petición de saveSale, que incluye InvoiceDTO y lista de productos.
     */
    public static record SaveSaleRequest(InvoiceDTO invoiceDTO, List<ProductBranchDTO> productStocksList) {
    }
}
