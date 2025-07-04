package com.interfija.masterposmultitenant.dto.sale;

import com.interfija.masterposmultitenant.dto.other.TypeUnitDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representa una venta diaria.
 *
 * @author Steven Arzuza.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaleDailyDTO {

    /**
     * Identificador único de la factura.
     */
    private Long idInvoice;

    /**
     * Fecha y hora de la factura.
     */
    private LocalDateTime date;

    /**
     * Terminal asociada a la factura.
     */
    private String terminal;

    private String barcode;

    private String category;

    private String product;

    private TypeUnitDTO typeUnitDTO;

    private BigDecimal quantity;

    private BigDecimal purchasePrice;

    private BigDecimal salePrice;

    private BigDecimal priceFinal;

    private String discountType;

    private BigDecimal discountValue;

    private BigDecimal retention;

    /**
     * Cliente asociado a la factura.
     */
    private String customer;

    /**
     * Empleado asociado a la factura.
     */
    private String employee;

    private String commissionType;

    private BigDecimal commissionValue;

    private String payments;

    private BigDecimal totalTax;

    private BigDecimal totalSale;

    private BigDecimal totalRetention;

    private BigDecimal totalSaleFinal;

    private BigDecimal totalUtility;

}
