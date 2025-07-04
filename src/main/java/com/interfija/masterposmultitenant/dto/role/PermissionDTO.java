package com.interfija.masterposmultitenant.dto.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representa un permiso.
 *
 * @author Steven Arzuza.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
public class PermissionDTO {

    /**
     * Identificador único del permiso.
     */
    private short idPermission;

    /**
     * Nombre del permiso.
     */
    private String name;

    /**
     * Identificador del permiso padre, si existe.
     */
    private PermissionDTO parentDTO;

    private List<PermissionDTO> childrenList;

    public PermissionDTO() {
        this.childrenList = new ArrayList<>();
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
        PermissionDTO that = (PermissionDTO) obj;
        return idPermission == that.idPermission;
    }

    /**
     * Devuelve el código hash de este objeto.
     *
     * @return el código hash basado en el identificador del permiso.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idPermission);
    }

    /**
     * Devuelve una representación en forma de cadena del nombre del permiso.
     *
     * @return el nombre del permiso.
     */
    @Override
    public String toString() {
        return name;
    }

}
