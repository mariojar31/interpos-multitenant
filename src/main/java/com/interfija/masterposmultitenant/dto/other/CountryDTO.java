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
 * Representa la información de un país.
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
public class CountryDTO {

    /**
     * Identificador único del país.
     */
    private short idCountry;

    /**
     * Nombre del país.
     */
    private String name;

    /**
     * Código único del país.
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
        CountryDTO that = (CountryDTO) obj;
        return idCountry == that.idCountry;
    }

    /**
     * Devuelve el código hash de este objeto.
     *
     * @return el código hash basado en el identificador del país.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idCountry);
    }

    /**
     * Devuelve una representación en forma de cadena del nombre del país.
     *
     * @return el nombre del país.
     */
    @Override
    public String toString() {
        return name;
    }

}
