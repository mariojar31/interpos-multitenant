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
 * Representa la información de un tipo de documento electrónico.
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
public class TypeElectronicDocumentDTO {

    /**
     * Identificador único del tipo de documento electrónico.
     */
    private short idTypeElectronicDocument;

    /**
     * Nombre del tipo de documento electrónico.
     */
    private String name;

    private short apiElectronicDocumentId;

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
        TypeElectronicDocumentDTO that = (TypeElectronicDocumentDTO) obj;
        return idTypeElectronicDocument == that.idTypeElectronicDocument;
    }

    /**
     * Devuelve el código hash de este objeto.
     *
     * @return el código hash basado en el identificador del tipo de documento electrónico.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idTypeElectronicDocument);
    }

    /**
     * Devuelve una representación en forma de cadena del nombre del tipo de documento electrónico.
     *
     * @return el nombre del tipo de documento electrónico.
     */
    @Override
    public String toString() {
        return name;
    }

}
