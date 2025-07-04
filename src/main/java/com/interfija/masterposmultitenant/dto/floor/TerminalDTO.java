package com.interfija.masterposmultitenant.dto.floor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * Representa una terminal.
 *
 * @author Steven Arzuza.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TerminalDTO {

    /**
     * Identificador único del terminal.
     */
    private Long idTerminal;

    /**
     * Código del terminal.
     */
    private String code;

    /**
     * Nombre del terminal.
     */
    private String name;

    /**
     * Secuencia de la terminal.
     */
    private int sequence;

    /**
     * Indica si el terminal es visible o no.
     */
    private boolean visible;

    /**
     * Piso asociado a la terminal.
     */
    private FloorDTO floorDTO;

    public TerminalDTO(Long idTerminal, String name) {
        this.idTerminal = idTerminal;
        this.name = name;
    }

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
        TerminalDTO that = (TerminalDTO) obj;
        return Objects.equals(idTerminal, that.idTerminal);
    }

    /**
     * Devuelve el código hash de este objeto.
     *
     * @return el código hash basado en el identificador del terminal.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idTerminal);
    }

    /**
     * Devuelve una representación en forma de cadena del nombre del terminal.
     *
     * @return el nombre del terminal.
     */
    @Override
    public String toString() {
        return name == null ? "" : name;
    }

}
