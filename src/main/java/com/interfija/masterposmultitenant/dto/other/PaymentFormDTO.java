package com.interfija.masterposmultitenant.dto.other;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * Representa un forma de pago.
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
public class PaymentFormDTO {

    /**
     * Identificador único del forma de pago.
     */
    private short idPaymentForm;

    /**
     * Nombre del forma de pago.
     */
    private String name;

    /**
     * Código del forma de pago.
     */
    private String code;

    /**
     * Compara este objeto con otro para determinar si son iguales.
     * La comparación se basa en el identificador del forma de pago.
     *
     * @param obj el objeto a comparar.
     * @return {@code true} si ambos objetos tienen el mismo identificador de forma de pago, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PaymentFormDTO that = (PaymentFormDTO) obj;
        return idPaymentForm == that.idPaymentForm;
    }

    /**
     * Devuelve el código hash de este objeto basado en el identificador del forma de pago.
     *
     * @return el código hash del identificador de forma de pago.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idPaymentForm);
    }

    /**
     * Devuelve una representación en forma de cadena del nombre del forma de pago.
     *
     * @return el nombre del forma de pago.
     */
    @Override
    public String toString() {
        return name == null ? "" : name;
    }

}
