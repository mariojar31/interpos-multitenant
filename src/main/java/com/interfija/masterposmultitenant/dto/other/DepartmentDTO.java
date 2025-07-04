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
 * Representa la información de un departamento.
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
public class DepartmentDTO {

    /**
     * Identificador único del departamento.
     */
    private short idDepartment;

    /**
     * Nombre del departamento.
     */
    private String name;

    /**
     * Identificador del país al que pertenece el departamento.
     */
    private CountryDTO countryDTO;

    /**
     * Código único del departamento.
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
        DepartmentDTO that = (DepartmentDTO) obj;
        return idDepartment == that.idDepartment;
    }

    /**
     * Devuelve el código hash de este objeto.
     *
     * @return el código hash basado en el identificador del departamento.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idDepartment);
    }

    /**
     * Devuelve una representación en forma de cadena del nombre del departamento.
     *
     * @return el nombre del departamento.
     */
    @Override
    public String toString() {
        return name == null ? "" : name;
    }

}
