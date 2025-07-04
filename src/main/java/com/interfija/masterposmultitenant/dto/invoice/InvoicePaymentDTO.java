package com.interfija.masterposmultitenant.dto.invoice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.interfija.masterposmultitenant.dto.payment.PaymentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * Representa el pago asociado a la factura.
 *
 * @author Steven Arzuza.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoicePaymentDTO {

    /**
     * Identificador único del pago asociado a la factura
     */
    private Long idInvoicePayment;

    /**
     * Información del pago asociado a la factura.
     */
    private PaymentDTO paymentDTO;

    /**
     * Identificador único asociado a la factura
     */
    private Long invoiceId;

    /**
     * Crea una nueva instancia de {@code InvoicePaymentDTO} con la información de pago proporcionada.
     *
     * @param paymentDTO El pago asociado a la factura.
     */
    public InvoicePaymentDTO(PaymentDTO paymentDTO) {
        this.paymentDTO = paymentDTO;
    }

    /**
     * Determina si este objeto es igual a otro.
     *
     * @param obj el objeto a comparar.
     * @return {@code true} si ambos objetos son iguales, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        InvoicePaymentDTO that = (InvoicePaymentDTO) obj;
        return Objects.equals(idInvoicePayment, that.idInvoicePayment);
    }

    /**
     * Devuelve el código hash de este objeto.
     *
     * @return el código hash basado en el identificador de pago asociado a la factura.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idInvoicePayment);
    }

    /**
     * Devuelve una representación en forma de cadena del nombre completo de pago.
     *
     * @return el nombre completo de pago (primer nombre y apellido).
     */
    @Override
    public String toString() {
        if (paymentDTO != null) {
            return paymentDTO.toString();
        }
        return "";
    }

}
