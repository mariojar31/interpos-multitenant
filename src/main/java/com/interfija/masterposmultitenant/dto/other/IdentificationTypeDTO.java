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
 * Representa el tipo de identificación.
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
public class IdentificationTypeDTO {

    /**
     * Identificador único del tipo de identificación.
     */
    private short idIdentificationType;

    /**
     * Nombre del tipo de identificación.
     */
    private String name;

    /**
     * Código único del tipo de identificación.
     */
    private String code;

    /**
     * Abreviatura del tipo de identificación.
     */
    private String abbreviation;

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
        IdentificationTypeDTO that = (IdentificationTypeDTO) obj;
        return idIdentificationType == that.idIdentificationType;
    }

    /**
     * Devuelve el código hash de este objeto.
     *
     * @return el código hash basado en el identificador del tipo de identificación.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idIdentificationType);
    }

    /**
     * Devuelve una representación en forma de cadena del nombre del tipo de identificación.
     *
     * @return el nombre del tipo de identificación.
     */
    @Override
    public String toString() {
        return name == null ? "" : name;
    }

}
