package com.interfija.masterposmultitenant.controller.tenant.sale;

import com.interfija.masterposmultitenant.dto.sale.CommissionInvoiceManualDTO;
import com.interfija.masterposmultitenant.dto.sale.CommissionProductDTO;
import com.interfija.masterposmultitenant.dto.sale.SaleDailyDTO;
import com.interfija.masterposmultitenant.dto.sale.SaleSummaryDTO;
import com.interfija.masterposmultitenant.services.sale.SalesReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controlador REST para los reportes de ventas y comisiones.
 *
 * Autor: OpenAI para Steven Arzuza
 */
@RestController
@RequestMapping("/api/tenant/sales/reports")
public class SalesReportController {

    private final SalesReportService salesReportService;

    @Autowired
    public SalesReportController(SalesReportService salesReportService) {
        this.salesReportService = salesReportService;
    }

    /**
     * Obtiene el resumen de ventas entre fechas para una sucursal.
     */
    @GetMapping("/summary")
    public List<SaleSummaryDTO> getSalesSummaries(
            @RequestParam long branchId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return salesReportService.getSalesSummaries(branchId, startDate, endDate);
    }

    /**
     * Obtiene el detalle diario de ventas entre fechas para una sucursal.
     */
    @GetMapping("/daily")
    public List<SaleDailyDTO> getSalesDaily(
            @RequestParam long branchId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return salesReportService.getSalesDaily(branchId, startDate, endDate);
    }

    /**
     * Obtiene el resumen de comisiones por productos entre fechas para una sucursal.
     */
    @GetMapping("/commissions/products")
    public List<CommissionProductDTO> getCommissionProductSummaries(
            @RequestParam long branchId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return salesReportService.getCommissionProductSummaries(branchId, startDate, endDate);
    }

    /**
     * Obtiene el resumen de comisiones manuales de facturas entre fechas para una sucursal.
     */
    @GetMapping("/commissions/invoices/manual")
    public List<CommissionInvoiceManualDTO> getCommissionInvoiceManualSummaries(
            @RequestParam long branchId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return salesReportService.getCommissionInvoiceManualSummaries(branchId, startDate, endDate);
    }
}
