package com.interfija.masterposmultitenant.dto.other;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * Representa un tipo de pago.
 *
 * @author Steven Arzuza.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TypePaymentDTO {

    /**
     * Identificador único del tipo de pago.
     */
    private short idTypePayment;

    /**
     * Nombre del tipo de pago.
     */
    private String name;

    private short apiPaymentId;

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
        TypePaymentDTO that = (TypePaymentDTO) obj;
        return idTypePayment == that.idTypePayment;
    }

    /**
     * Devuelve el código hash de este objeto.
     *
     * @return el código hash basado en el identificador del tipo de pago.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idTypePayment);
    }

    /**
     * Devuelve una representación en forma de cadena del nombre del tipo de pago.
     *
     * @return el nombre del tipo de pago.
     */
    @Override
    public String toString() {
        return name == null ? "" : name;
    }

}
