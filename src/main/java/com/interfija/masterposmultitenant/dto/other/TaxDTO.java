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
 * Representa un impuesto.
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
public class TaxDTO {

    /**
     * Identificador único del impuesto.
     */
    private short idTax;

    /**
     * Nombre del impuesto.
     */
    private String name;

    /**
     * Código del impuesto.
     */
    private String code;

    private short apiTaxId;

    /**
     * Compara este objeto con otro para determinar si son iguales.
     * La comparación se basa en el identificador del impuesto.
     *
     * @param obj el objeto a comparar.
     * @return {@code true} si ambos objetos tienen el mismo identificador de impuesto, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TaxDTO that = (TaxDTO) obj;
        return idTax == that.idTax;
    }

    /**
     * Devuelve el código hash de este objeto basado en el identificador del impuesto.
     *
     * @return el código hash del identificador de impuesto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idTax);
    }

    /**
     * Devuelve una representación en forma de cadena del nombre del impuesto.
     *
     * @return el nombre del impuesto.
     */
    @Override
    public String toString() {
        return name == null ? "" : name;
    }

}
