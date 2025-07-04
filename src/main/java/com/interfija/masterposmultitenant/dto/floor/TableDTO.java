package com.interfija.masterposmultitenant.dto.floor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * Representa una mesa.
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
public class TableDTO {

    /**
     * Identificador único de la mesa.
     */
    private Long idTable;

    /**
     * Código de la mesa.
     */
    private String code;

    /**
     * Nombre de la mesa.
     */
    private String name;

    /**
     * Posición en el eje 'x' de la mesa en el piso.
     */
    private int positionX;

    /**
     * Posición en el eje 'y' de la mesa en el piso.
     */
    private int positionY;

    /**
     * Ancho de la mesa.
     */
    private int width;

    /**
     * Altura de la mesa.
     */
    private int height;

    /**
     * Indica si la mesa es visible o no en la plataforma.
     */
    private boolean visible;

    /**
     * Piso asociado a la mesa.
     */
    private FloorDTO floorDTO;

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
        TableDTO that = (TableDTO) obj;
        return Objects.equals(idTable, that.idTable);
    }

    /**
     * Devuelve el código hash de este objeto.
     *
     * @return el código hash basado en el identificador de la mesa.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idTable);
    }

    /**
     * Devuelve una representación en forma de cadena del nombre de la mesa.
     *
     * @return el nombre de la mesa.
     */
    @Override
    public String toString() {
        return name == null ? "" : name;
    }

}
