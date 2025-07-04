package com.interfija.masterposmultitenant.controller.tenant.invoice;

import com.interfija.masterposmultitenant.dto.invoice.InvoiceDTO;
import com.interfija.masterposmultitenant.services.invoice.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para gestionar operaciones sobre facturas.
 * Permite la creación, consulta, actualización y eliminación de facturas.
 */
@RestController
@RequestMapping("/api/tenant/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    /**
     * Obtiene una factura por su ID.
     *
     * @param id ID de la factura.
     * @param fullMapping Si se desea la factura con todo su detalle.
     * @return Detalles de la factura.
     */
    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDTO> getInvoiceById(
            @PathVariable long id,
            @RequestParam(defaultValue = "true") boolean fullMapping
    ) {
        Optional<InvoiceDTO> invoice = invoiceService.getInvoiceById(id, fullMapping);
        return invoice.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Obtiene todas las facturas dentro de un rango de fechas para una sucursal.
     *
     * @param branchId ID de la sucursal.
     * @param startDate Fecha de inicio.
     * @param endDate Fecha de fin.
     * @return Lista de facturas.
     */
    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<InvoiceDTO>> getInvoicesByBranchAndDates(
            @PathVariable long branchId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        List<InvoiceDTO> invoices = invoiceService.getInvoices(branchId, startDate, endDate);
        return ResponseEntity.ok(invoices);
    }

    /**
     * Crea o actualiza una factura.
     *
     * @param invoiceDTO Datos de la factura.
     * @return true si fue exitoso.
     */
    @PostMapping
    public ResponseEntity<Boolean> saveInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        boolean result = invoiceService.saveInvoice(invoiceDTO);
        return ResponseEntity.ok(result);
    }

    /**
     * Elimina una factura.
     *
     * @param invoiceDTO Datos de la factura a eliminar.
     * @return true si fue exitoso.
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        boolean result = invoiceService.deleteInvoice(invoiceDTO);
        return ResponseEntity.ok(result);
    }
}
