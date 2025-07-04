package com.interfija.masterposmultitenant.dto.sale;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representa la comisión de un producto de factura.
 *
 * @author Steven Arzuza.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommissionInvoiceManualDTO {

    /**
     * Fecha y hora de la factura.
     */
    private LocalDateTime date;

    private String numberInvoice;

    /**
     * Cliente asociado a la factura.
     */
    private String customer;

    private String customerPhone;

    /**
     * Empleado asociado a la factura.
     */
    private String employee;

    private String commissionType;

    private BigDecimal commissionValue;

    private String branch;

    private String company;

    private BigDecimal subtotal;

    private BigDecimal total;

    private BigDecimal totalCommission;

}
