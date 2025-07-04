package com.interfija.masterposmultitenant.dto.base;

import com.interfija.masterposmultitenant.utils.ActionEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * Representa DTO base con acciones de crud.
 *
 * @author Steven Arzuza.
 */
@Setter
@Getter
public abstract class DtoWithAction {

    /**
     * Acción asociada al DTO (por ejemplo, "inserta", "actualizar", "eliminar", "ninguna").
     */
    protected ActionEnum action = ActionEnum.NONE;
}
