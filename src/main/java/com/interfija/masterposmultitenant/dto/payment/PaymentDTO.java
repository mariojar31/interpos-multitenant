package com.interfija.masterposmultitenant.dto.payment;

import com.interfija.masterposmultitenant.dto.base.DtoWithAction;
import com.interfija.masterposmultitenant.dto.other.TypePaymentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Representa un pago.
 *
 * @author Steven Arzuza.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO extends DtoWithAction {

    /**
     * Identificador único del pago.
     */
    private long idPayment;

    /**
     * Tipo de pago asociado al pago
     */
    private TypePaymentDTO typePaymentDTO;

    /**
     * Total recibido para el pago.
     */
    private BigDecimal totalReceived;

    /**
     * Total cambio del recibido si el pago es en efectivo.
     */
    private BigDecimal totalChange;

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
        PaymentDTO that = (PaymentDTO) obj;
        return idPayment == that.idPayment;
    }

    /**
     * Devuelve el código hash de este objeto.
     *
     * @return el código hash basado en el identificador del tipo de pago.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idPayment);
    }

    /**
     * Devuelve una representación en forma de cadena del nombre completo del cliente.
     *
     * @return el nombre completo del cliente (primer nombre y apellido).
     */
    @Override
    public String toString() {
        if (typePaymentDTO != null) {
            return typePaymentDTO.toString();
        }

        return "";
    }

}
