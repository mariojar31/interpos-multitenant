package com.interfija.masterposmultitenant.dto.sale;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representa el resumen de una factura.
 *
 * @author Steven Arzuza.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaleSummaryDTO {

    /**
     * Identificador único de la factura.
     */
    private Long idInvoice;

    /**
     * Fecha y hora de la factura.
     */
    private LocalDateTime date;

    /**
     * Cliente asociado a la factura.
     */
    private String customer;

    /**
     * Empleado asociado a la factura.
     */
    private String employee;

    /**
     * Total de la factura.
     */
    private BigDecimal total;

}
