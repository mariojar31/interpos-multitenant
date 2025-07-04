package com.interfija.masterposmultitenant.services.employee;

import com.interfija.masterposmultitenant.dto.branch.BranchDTO;
import com.interfija.masterposmultitenant.dto.company.CompanyDTO;
import com.interfija.masterposmultitenant.dto.employee.EmployeeDTO;
import com.interfija.masterposmultitenant.dto.role.RoleDTO;
import com.interfija.masterposmultitenant.entities.tenant.employee.EmployeeEntity;
import com.interfija.masterposmultitenant.mappers.employee.EmployeeMapper;
import com.interfija.masterposmultitenant.repository.employee.EmployeeRepository;
import com.interfija.masterposmultitenant.repository.employee.projections.EmployeeProjection;
import com.interfija.masterposmultitenant.services.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Clase que gestiona las operaciones relacionadas con los empleados y roles dentro del sistema.
 * Esta clase maneja las instancias de acceso a la base de datos para los empleados y los roles,
 * permitiendo realizar operaciones como obtener, crear y actualizar empleados y sus roles asociados.
 *
 * @author Steven Arzuza.
 */
@Service
public class EmployeeService extends BaseService {

    /**
     * Objeto que maneja las operaciones de acceso a la base de datos para los empleados.
     */
    private final EmployeeRepository employeeRepository;

    /**
     * Mapper para convertir entre entidades {@link EmployeeEntity} y {@link EmployeeDTO} relacionados.
     */
    private final EmployeeMapper employeeMapper;

    /**
     * Constructor que inicializa los objetos necesarios para la gestión de empleado.
     * Inicializa la fuente de datos, el DAO de empleado y los datos maestros.
     */
    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        setLogger(EmployeeService.class);
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    /**
     * Obtiene una empleado registrada en el sistema.
     *
     * @return Un objeto EmployeeDTO que representan la empleado registrada.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeDTO> getEmployee(Long idEmployee) {
        try {
            return employeeRepository.findById(idEmployee)
                    .map(employeeMapper::toDto);
        } catch (Exception e) {
            logger.error("No se pudo obtener el empleado -> {}.", e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Obtiene un empleado registrado en el sistema.
     *
     * @return Un objeto EmployeeDTO que representan el empleado registrado.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeDTO> getEmployee(Long companyId, String identificationNumber, boolean visible) {
        try {
            return employeeRepository.findEmployee(companyId, identificationNumber, visible)
                    .map(employeeMapper::toDto);
        } catch (Exception e) {
            logger.error("No se pudo obtener el empleado con identificación '{}' -> {}.", identificationNumber, e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Obtiene una lista básica de empleados visibles en el sistema.
     * <p>
     * Esta lista contiene únicamente los campos esenciales de la empresa:
     * ID y nombre.
     *
     * @param visible Indica si se deben incluir solo las empleados visibles.
     * @return Una lista de objetos {@link EmployeeDTO} con los datos básicos de las empleados.
     */
    @Transactional(readOnly = true)
    public List<EmployeeDTO> getEmployeeBasicList(boolean visible) {
        try {
            return getEmployeeBasicList(employeeRepository.findAllProjectedBasicByVisible(visible));
        } catch (Exception e) {
            logger.error("No se pudo obtener la lista basica de empleados -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Convierte una lista de objetos {@link EmployeeProjection} en una lista básica de {@link EmployeeDTO},
     * mapeando los datos relevantes del empleado.
     *
     * @param projectionList la lista de proyecciones de empleados a convertir.
     * @return una lista de objetos {@link EmployeeDTO} construidos a partir de la lista de entrada.
     */
    private List<EmployeeDTO> getEmployeeBasicList(List<EmployeeProjection> projectionList) {
        return projectionList
                .stream()
                .map(p ->
                        EmployeeDTO.builder()
                                .idEmployee(p.getIdEmployee())
                                .names(p.getNames())
                                .lastNames(p.getLastNames())
                                .build()
                )
                .toList();
    }

    /**
     * Obtiene una lista básica de empleados visibles en el sistema.
     * <p>
     * Esta lista contiene únicamente los campos esenciales del empleado:
     * ID y nombre.
     *
     * @param visible   Indica si se deben incluir solo las empleados visibles.
     * @param companyId Identificador de la empresa.
     * @return Una lista de objetos {@link EmployeeDTO} con los datos básicos de las empleados.
     */
    @Transactional(readOnly = true)
    public List<EmployeeDTO> getEmployeeBasicList(Long companyId, boolean visible) {
        try {
            return getEmployeeBasicList(employeeRepository.findAllProjectedBasicByCompanyIdAndVisible(companyId, visible));
        } catch (Exception e) {
            logger.error("No se pudo obtener la lista basica de empleados por empresa -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Obtiene una lista detallada (resumen) de empleados visibles en el sistema.
     * <p>
     * Esta lista incluye información adicional además del ID y nombre,
     * como dirección, identificador de la sucursal y su nombre.
     *
     * @param visible Indica si se deben incluir solo las empleados visibles.
     * @return Una lista de objetos {@link EmployeeDTO} con datos resumidos de las empleados.
     */
    @Transactional(readOnly = true)
    public List<EmployeeDTO> getEmployeeSummaryList(boolean visible) {
        try {
            return getEmployeeSummaryList(employeeRepository.findAllProjectedSummaryByVisible(visible));
        } catch (Exception e) {
            logger.error("No se pudo obtener la lista detallada de empleados -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Convierte una lista de objetos {@link EmployeeProjection} en una lista detallada de {@link EmployeeDTO},
     * mapeando los datos relevantes de los empleados y sus respectivas sucursales.
     *
     * @param projectionList la lista de proyecciones de los empleados a convertir.
     * @return una lista de objetos {@link EmployeeDTO} construidos a partir de la lista de entrada.
     */
    private List<EmployeeDTO> getEmployeeSummaryList(List<EmployeeProjection> projectionList) {
        return projectionList
                .stream()
                .map(p -> {
                    CompanyDTO companyDTO = CompanyDTO.builder()
                            .idCompany(p.getCompanyId())
                            .name(p.getCompanyName())
                            .build();

                    BranchDTO branchDTO = BranchDTO.builder()
                            .idBranch(p.getBranchId())
                            .name(p.getBranchName())
                            .companyDTO(companyDTO)
                            .build();

                    RoleDTO roleDTO = RoleDTO.builder()
                            .idRole(p.getRoleId())
                            .name(p.getRoleName())
                            .build();

                    return EmployeeDTO.builder()
                            .idEmployee(p.getIdEmployee())
                            .names(p.getNames())
                            .lastNames(p.getLastNames())
                            .identificationNumber(p.getIdentificationNumber())
                            .phone(p.getPhone())
                            .roleDTO(roleDTO)
                            .branchDTO(branchDTO)
                            .build();
                })
                .toList();
    }

    /**
     * Obtiene una lista detallada (resumen) de empleados por empresa y visibles en el sistema.
     * <p>
     * Esta lista incluye información adicional además del ID y nombre,
     * como dirección, identificador de la sucursal y su nombre.
     *
     * @param visible   Indica si se deben incluir solo las empleados visibles.
     * @param companyId Identificador de la empresa.
     * @return Una lista de objetos {@link BranchDTO} con datos resumidos de las empleados.
     */
    @Transactional(readOnly = true)
    public List<EmployeeDTO> getEmployeeSummaryList(Long companyId, boolean visible) {
        try {
            return getEmployeeSummaryList(employeeRepository.findAllProjectedSummaryByCompanyIdAndVisible(companyId, visible));
        } catch (Exception e) {
            logger.error("No se pudo obtener la lista detallada de empleados por empresa -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Obtiene una lista de empleados por rol registrada en el sistema.
     *
     * @return Una lista de objetos SellerDTO que representan los empleados registrados.
     */
    @Transactional(readOnly = true)
    public List<EmployeeDTO> getEmployeeByCompanyAndRoleConditionList(Long companyId, Short roleId, boolean visible) {
        List<EmployeeEntity> employeeList = employeeRepository.findEmployeesByCompanyIdAndRoleCondition(companyId, visible, roleId == 6);
        return employeeMapper.toDtoList(employeeList);
    }

    @Transactional(readOnly = true)
    public List<EmployeeDTO> getEmployeeByBranchAndRoleConditionList(Long branchId, Short roleId, boolean visible) {
        List<EmployeeEntity> employeeList = employeeRepository.findEmployeesByBranchIdAndRoleCondition(branchId, visible, roleId == 6);
        return employeeMapper.toDtoList(employeeList);
    }

    /**
     * Inserta, actualiza la información del empleado.
     *
     * @param employeeDTO un Objeto EmployeeDTO que representa los datos del empleado.
     * @return {@code true} si la operación fue exitosa, {@code false} en caso contrario.
     */
    public boolean saveEmployee(EmployeeDTO employeeDTO) {
        if (employeeDTO.getIdEmployee() == null) {
            return insertEmployee(employeeDTO);
        } else {
            return updateEmployee(employeeDTO);
        }
    }

    /**
     * Inserta un nuevo empleado en la base de datos a partir del DTO proporcionado.
     *
     * @param employeeDTO el objeto DTO que contiene los datos del empleado a insertar
     * @return {@code true} si la empleado fue insertada correctamente (es decir, si se generó un ID); {@code false} en caso de error
     */
    @Transactional
    protected boolean insertEmployee(EmployeeDTO employeeDTO) {
        try {
            EmployeeEntity employeeEntity = employeeMapper.toEntity(employeeDTO);
            employeeRepository.save(employeeEntity);
            return employeeEntity.getIdEmployee() != null;
        } catch (Exception e) {
            logger.error("No se pudo insertar el empleado -> {}.", e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza la información de un empleado existente en la base de datos utilizando el DTO proporcionado.
     *
     * @param employeeDTO el objeto DTO con los datos actualizados del empleado
     * @return {@code true} si la actualización fue exitosa (es decir, si el ID sigue presente); {@code false} en caso de error
     */
    @Transactional
    protected boolean updateEmployee(EmployeeDTO employeeDTO) {
        try {
            EmployeeEntity employeeEntity = employeeMapper.toEntity(employeeDTO);
            employeeRepository.save(employeeEntity);
            return true;
        } catch (Exception e) {
            logger.error("No se pudo actualizar el empleado -> {}.", e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un empleado de la base de datos según los datos del DTO proporcionado.
     *
     * @param employeeDTO el objeto DTO que representa el empleado a eliminar
     * @return {@code true} si el empleado fue eliminada correctamente; {@code false} en caso de error
     */
    @Transactional
    public boolean deleteEmployee(EmployeeDTO employeeDTO) {
        try {
            employeeRepository.delete(employeeMapper.toEntity(employeeDTO));
            return true;
        } catch (Exception e) {
            logger.error("No se pudo eliminar el empleado -> {}.", e.getMessage());
            return false;
        }
    }

}
