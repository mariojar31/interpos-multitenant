package com.interfija.masterposmultitenant.services.login;

import com.interfija.masterposmultitenant.dto.branch.BranchDTO;
import com.interfija.masterposmultitenant.dto.cash.CashDTO;
import com.interfija.masterposmultitenant.dto.company.CompanyDTO;
import com.interfija.masterposmultitenant.dto.floor.FloorDTO;
import com.interfija.masterposmultitenant.dto.floor.TerminalDTO;
import com.interfija.masterposmultitenant.dto.session.SessionDTO;
import com.interfija.masterposmultitenant.services.base.BaseService;
import com.interfija.masterposmultitenant.services.branch.BranchService;
import com.interfija.masterposmultitenant.services.cash.CashService;
import com.interfija.masterposmultitenant.services.company.CompanyService;
import com.interfija.masterposmultitenant.services.employee.EmployeeService;
import com.interfija.masterposmultitenant.services.floor.FloorService;
import com.interfija.masterposmultitenant.services.role.RoleService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Clase que gestiona las operaciones relacionadas con el inicio de sesión del sistema.
 * Esta clase maneja las instancias de acceso a los servicios necesarios para la sesión.
 *
 * @author Steven Arzuza.
 */
@Service
public class LoginService extends BaseService {

    /**
     * Objeto que maneja las operaciones de servicio a los datos para la empresa.
     */
    @Getter
    private final CompanyService companyService;

    /**
     * Objeto que maneja las operaciones de servicio a los datos para las sucursales.
     */
    @Getter
    private final BranchService branchService;

    /**
     * Objeto que maneja las operaciones de servicio a los datos para los pisos.
     */
    @Getter
    private final FloorService floorService;

    /**
     * Objeto que maneja las operaciones de servicio a los datos para la caja.
     */
    private final CashService cashService;

    /**
     * Objeto que maneja las operaciones de servicio a los datos para los empleados.
     */
    @Getter
    private final EmployeeService employeeService;

    /**
     * Objeto que maneja las operaciones de servicio a los datos para los roles.
     */
    @Getter
    private final RoleService roleService;

    /**
     * Constructor que inicializa los objetos necesarios para la gestión de inicio de session del sistema.
     * Inicializa la fuente de datos y otros servicios.
     */
    @Autowired
    public LoginService(CompanyService companyService, BranchService branchService, FloorService floorService,
                        CashService cashService, EmployeeService employeeService, RoleService roleService) {
        this.companyService = companyService;
        this.branchService = branchService;
        this.floorService = floorService;
        this.cashService = cashService;
        this.employeeService = employeeService;
        this.roleService = roleService;
    }

    /**
     * Obtiene una sesión completa a partir del código de terminal proporcionado.
     * Recupera en cascada los datos relacionados: terminal, caja, piso, sucursal y empresa.
     *
     * @param terminal el código de terminal a consultar.
     * @return un {@link Optional} que contiene el {@link SessionDTO} si todos los datos fueron encontrados;
     * en caso contrario, un Optional vacío.
     */
    @Transactional(readOnly = true)
    public Optional<SessionDTO> getSession(String terminal) {
        if (terminal == null || terminal.isEmpty()) {
            return Optional.empty();
        }

        Optional<TerminalDTO> optionalTerminalDTO = floorService.getTerminalByName(terminal);
        if (optionalTerminalDTO.isEmpty()) {
            return Optional.empty();
        }

        TerminalDTO terminalDTO = optionalTerminalDTO.get();
        Optional<CashDTO> optionalCashDTO = cashService.getOpenCashByTerminalId(terminalDTO.getIdTerminal(), false);
        if (optionalCashDTO.isEmpty()) {
            return Optional.empty();
        }

        Optional<FloorDTO> optionalFloorDTO = floorService.getFloorBasic(terminalDTO.getFloorDTO().getIdFloor());
        if (optionalFloorDTO.isEmpty()) {
            return Optional.empty();
        }

        FloorDTO floorDTO = optionalFloorDTO.get();
        Optional<BranchDTO> optionalBranchDTO = branchService.getBranch(floorDTO.getBranchDTO().getIdBranch());
        if (optionalBranchDTO.isEmpty()) {
            return Optional.empty();
        }

        BranchDTO branchDTO = optionalBranchDTO.get();
        Optional<CompanyDTO> optionalCompanyDTO = companyService.getCompany(branchDTO.getCompanyDTO().getIdCompany());
        if (optionalCompanyDTO.isEmpty()) {
            return Optional.empty();
        }

        SessionDTO sessionDTO = SessionDTO.builder()
                .cashDTO(optionalCashDTO.get())
                .terminalDTO(terminalDTO)
                .floorDTO(floorDTO)
                .branchDTO(branchDTO)
                .companyDTO(optionalCompanyDTO.get())
                .build();

        return Optional.of(sessionDTO);
    }

}
