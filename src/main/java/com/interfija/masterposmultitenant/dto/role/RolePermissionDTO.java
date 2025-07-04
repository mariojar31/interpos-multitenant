package com.interfija.masterposmultitenant.dto.role;

import com.interfija.masterposmultitenant.dto.base.DtoWithAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa la relación entre el rol y un permiso.
 *
 * @author Steven Arzuza.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RolePermissionDTO extends DtoWithAction {

    /**
     * Identificador único del rol.
     */
    private short roleId;

    /**
     * Identificador único del permiso.
     */
    private short permissionId;

    /**
     * Identificador único de la sucursal asociada al rol y el permiso.
     */
    private long branchId;

}
