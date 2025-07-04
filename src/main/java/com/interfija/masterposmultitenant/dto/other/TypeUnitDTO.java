package com.interfija.masterposmultitenant.dto.other;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Representa una unidad de medida.
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
public class TypeUnitDTO {

    /**
     * Identificador único de la unidad de medida.
     */
    private short idTypeUnit;

    /**
     * Nombre de la unidad de medida.
     */
    private String name;

    /**
     * Abreviatura de la unidad de medida.
     */
    private String abbreviation;

    /**
     * Valor base de la unidad de medida.
     */
    private BigDecimal baseValue;

    private short apiUnitId;

    /**
     * Compara este objeto con otro para determinar si son iguales.
     * La comparación se basa en el identificador de la unidad.
     *
     * @param obj el objeto a comparar.
     * @return {@code true} si ambos objetos tienen el mismo identificador de unidad, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TypeUnitDTO that = (TypeUnitDTO) obj;
        return idTypeUnit == that.idTypeUnit;
    }

    /**
     * Devuelve el código hash de este objeto basado en el identificador de la unidad.
     *
     * @return el código hash del identificador de unidad.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idTypeUnit);
    }

    /**
     * Devuelve una representación en forma de cadena del nombre de la unidad de medida.
     *
     * @return el nombre de la unidad de medida.
     */
    @Override
    public String toString() {
        return name == null ? "" : name;
    }

}
