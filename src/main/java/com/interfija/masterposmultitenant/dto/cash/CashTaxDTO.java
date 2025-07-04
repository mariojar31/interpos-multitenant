package com.interfija.masterposmultitenant.dto.cash;

import com.interfija.masterposmultitenant.dto.other.TypeTaxDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Representa la información de los impuestos de la caja.
 *
 * @author Steven Arzuza.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CashTaxDTO {

    /**
     * Impuesto asociado caja.
     */
    private TypeTaxDTO typeTaxDTO;

    /**
     * Total del impuesto asociado caja.
     */
    private BigDecimal total;

    /**
     * Total neto al que se le sacó el total del impuesto de la caja.
     */
    private BigDecimal neto;

    /**
     * Identificador único de la factura asociada a la caja.
     */
    private Long invoiceId;

    /**
     * Identificador único de la caja.
     */
    private Long cashId;
}
