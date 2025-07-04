package com.interfija.masterposmultitenant.dto.floor;

import com.interfija.masterposmultitenant.dto.branch.BranchDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representa un piso.
 *
 * @author Steven Arzuza.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
public class FloorDTO {

    /**
     * Identificador único del piso.
     */
    private Long idFloor;

    /**
     * Nombre del piso.
     */
    private String name;

    /**
     * Imagen del piso.
     */
    private byte[] image;

    /**
     * Lista de terminales asociadas al piso.
     */
    private List<TerminalDTO> terminalsList;

    /**
     * Lista de mesas asociadas al piso.
     */
    private List<TableDTO> tablesList;

    /**
     * Indica si el piso es visible o no.
     */
    private boolean visible;

    /**
     * Sucursal asociada al piso.
     */
    private BranchDTO branchDTO;

    /**
     * Constructor vacío, inicializa las listas del piso.
     */
    public FloorDTO() {
        this.terminalsList = new ArrayList<>();
        this.tablesList = new ArrayList<>();
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
        FloorDTO that = (FloorDTO) obj;
        return Objects.equals(idFloor, that.idFloor);
    }

    /**
     * Devuelve el código hash de este objeto.
     *
     * @return el código hash basado en el identificador del piso.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idFloor);
    }

    /**
     * Devuelve una representación en forma de cadena del nombre del piso.
     *
     * @return el nombre del piso.
     */
    @Override
    public String toString() {
        return name == null ? "" : name;
    }

}
