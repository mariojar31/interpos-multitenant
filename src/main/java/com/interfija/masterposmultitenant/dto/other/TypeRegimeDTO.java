package com.interfija.masterposmultitenant.dto.other;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * Representa el tipo de regimen.
 *
 * @author Steven Arzuza.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TypeRegimeDTO {

    /**
     * Identificador único del tipo de regimen.
     */
    private short idTypeRegime;

    /**
     * Nombre del tipo de regimen.
     */
    private String name;

    /**
     * Código único del tipo de regimen.
     */
    private String code;

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
        TypeRegimeDTO that = (TypeRegimeDTO) obj;
        return idTypeRegime == that.idTypeRegime;
    }

    /**
     * Devuelve el código hash de este objeto.
     *
     * @return el código hash basado en el identificador del tipo de regimen.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idTypeRegime);
    }

    /**
     * Devuelve una representación en forma de cadena del nombre del tipo de regimen.
     *
     * @return el nombre del tipo de regimen.
     */
    @Override
    public String toString() {
        return name == null ? "" : name;
    }

}
