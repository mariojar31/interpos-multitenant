package com.interfija.masterposmultitenant.dto.role;

import com.interfija.masterposmultitenant.dto.base.DtoWithAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * Representa un rol.
 *
 * @author Steven Arzuza.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO extends DtoWithAction {

    /**
     * Identificador único del rol.
     */
    private short idRole;

    /**
     * Nombre del rol.
     */
    private String name;

    /**
     * Compara este objeto con otro para determinar si son iguales.
     * La comparación se basa en el identificador del rol.
     *
     * @param obj el objeto a comparar.
     * @return {@code true} si ambos objetos tienen el mismo identificador de rol, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RoleDTO that = (RoleDTO) obj;
        return idRole == that.idRole;
    }

    /**
     * Devuelve el código hash de este objeto basado en el identificador del rol.
     *
     * @return el código hash del identificador de rol.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idRole);
    }

    /**
     * Devuelve una representación en forma de cadena del nombre del rol.
     *
     * @return el nombre del rol.
     */
    @Override
    public String toString() {
        return name == null ? "" : name;
    }

}
