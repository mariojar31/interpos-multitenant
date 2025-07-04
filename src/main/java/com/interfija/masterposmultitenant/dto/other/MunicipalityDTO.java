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
 * Representa un municipio.
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
public class MunicipalityDTO {

    /**
     * Identificador único del municipio.
     */
    private long idMunicipality;

    /**
     * Nombre del municipio.
     */
    private String name;

    /**
     * Identificador del departamento al que pertenece el municipio.
     */
    private DepartmentDTO departmentDTO;

    /**
     * Código del municipio.
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
        MunicipalityDTO that = (MunicipalityDTO) obj;
        return idMunicipality == that.idMunicipality;
    }

    /**
     * Devuelve el código hash de este objeto.
     *
     * @return el código hash basado en el identificador del municipio.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idMunicipality);
    }

    /**
     * Devuelve una representación en forma de cadena del nombre del municipio.
     *
     * @return el nombre del municipio.
     */
    @Override
    public String toString() {
        return name == null ? "" : name;
    }

}
