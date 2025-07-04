package com.interfija.masterposmultitenant.dto.cash;

import com.interfija.masterposmultitenant.dto.other.TypePaymentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representa la información un pago de una caja.
 *
 * @author Steven Arzuza.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CashPaymentDTO {

    /**
     * Tipo de pago de la caja.
     */
    private TypePaymentDTO typePaymentDTO;

    /**
     * Total del pago de la caja.
     */
    private BigDecimal total;

    /**
     * Fecha del pago.
     */
    private LocalDateTime date;

    /**
     * Identificador único de la factura asociada a la caja.
     */
    private Long invoiceId;

    /**
     * Identificador único de la caja.
     */
    private Long cashId;
}
