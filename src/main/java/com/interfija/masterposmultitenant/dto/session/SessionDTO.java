package com.interfija.masterposmultitenant.dto.session;

import com.interfija.masterposmultitenant.dto.base.DtoWithAction;
import com.interfija.masterposmultitenant.dto.cash.CashDTO;
import com.interfija.masterposmultitenant.dto.company.CompanyDTO;
import com.interfija.masterposmultitenant.dto.employee.EmployeeDTO;
import com.interfija.masterposmultitenant.dto.floor.FloorDTO;
import com.interfija.masterposmultitenant.dto.floor.TerminalDTO;
import com.interfija.masterposmultitenant.dto.branch.BranchDTO;
import com.interfija.masterposmultitenant.dto.role.PermissionDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * Representa los datos de la sesión.
 *
 * @author Steven Arzuza.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionDTO extends DtoWithAction {

    /**
     * Empresa asociada a la sesión.
     */
    private CompanyDTO companyDTO;

    /**
     * Ubicación asociada a la sesión.
     */
    private BranchDTO branchDTO;

    /**
     * Piso asociado a la sesión.
     */
    private FloorDTO floorDTO;

    /**
     * Terminal asociado a la sesión.
     */
    private TerminalDTO terminalDTO;

    /**
     * Caja asociada a la sesión.
     */
    private CashDTO cashDTO;

    /**
     * Empleado asociado a la sesión.
     */
    private EmployeeDTO employeeDTO;

    /**
     * Mapa de permisos asociados al empleado, donde la clave es el identificador de permiso
     * y el valor es un {@link PermissionDTO} correspondiente.
     */
    private Map<Short, PermissionDTO> permissionsMap;

}
