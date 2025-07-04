package com.interfija.masterposmultitenant.services.supplier;

import com.interfija.masterposmultitenant.dto.branch.BranchDTO;
import com.interfija.masterposmultitenant.dto.company.CompanyDTO;
import com.interfija.masterposmultitenant.dto.supplier.SupplierDTO;
import com.interfija.masterposmultitenant.entities.tenant.supplier.SupplierEntity;
import com.interfija.masterposmultitenant.mappers.supplier.SupplierMapper;
import com.interfija.masterposmultitenant.repository.supplier.projections.SupplierProjection;
import com.interfija.masterposmultitenant.repository.supplier.SupplierRepository;
import com.interfija.masterposmultitenant.services.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Clase que gestiona las operaciones relacionadas con los proveedores y sus permisos dentro del sistema.
 * Esta clase maneja las instancias de acceso a la base de datos para los proveedores y sus permisos,
 * permitiendo realizar operaciones como obtener y actualizar proveedores y sus permisos asociados.
 *
 * @author Steven Arzuza.
 */
@Service
public class SupplierService extends BaseService {

    /**
     * Objeto que maneja las operaciones de acceso a la base de datos para los proveedores.
     */
    private final SupplierRepository supplierRepository;

    /**
     * Mapper para convertir entre entidades {@link SupplierEntity} y {@link SupplierDTO} relacionados.
     */
    private final SupplierMapper supplierMapper;

    /**
     * Constructor que inicializa el modelo de proveedores con la fuente de datos, el DAO de proveedores
     * y los datos maestros.
     */
    @Autowired
    public SupplierService(SupplierRepository supplierRepository, SupplierMapper supplierMapper) {
        setLogger(SupplierService.class);
        this.supplierRepository = supplierRepository;
        this.supplierMapper = supplierMapper;
    }

    /**
     * Obtiene una proveedor registrada en el sistema.
     *
     * @return Un objeto SupplierDTO que representan la proveedor registrada.
     */
    @Transactional(readOnly = true)
    public Optional<SupplierDTO> getSupplier(Long idSupplier) {
        try {
            return supplierRepository.findById(idSupplier)
                    .map(supplierMapper::toDto);
        } catch (Exception e) {
            logger.error("No se pudo obtener el proveedor -> {}.", e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Obtiene una lista básica de proveedores visibles en el sistema.
     * <p>
     * Esta lista contiene únicamente los campos esenciales de la empresa:
     * ID y nombre.
     *
     * @param visible Indica si se deben incluir solo las proveedores visibles.
     * @return Una lista de objetos {@link SupplierDTO} con los datos básicos de las proveedores.
     */
    @Transactional(readOnly = true)
    public List<SupplierDTO> getSupplierBasicList(boolean visible) {
        try {
            return getSupplierBasicList(supplierRepository.findAllProjectedBasicByVisible(visible));
        } catch (Exception e) {
            logger.error("No se pudo obtener la lista basica de proveedores -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Convierte una lista de objetos {@link SupplierProjection} en una lista básica de {@link SupplierDTO},
     * mapeando los datos relevantes del proveedor.
     *
     * @param projectionList la lista de proyecciones de proveedores a convertir.
     * @return una lista de objetos {@link SupplierDTO} construidos a partir de la lista de entrada.
     */
    private List<SupplierDTO> getSupplierBasicList(List<SupplierProjection> projectionList) {
        return projectionList
                .stream()
                .map(p ->
                        SupplierDTO.builder()
                                .idSupplier(p.getIdSupplier())
                                .names(p.getNames())
                                .lastNames(p.getLastNames())
                                .build()
                )
                .toList();
    }

    /**
     * Obtiene una lista básica de proveedores visibles en el sistema.
     * <p>
     * Esta lista contiene únicamente los campos esenciales del proveedor:
     * ID y nombre.
     *
     * @param visible   Indica si se deben incluir solo las proveedores visibles.
     * @param companyId Identificador de la empresa.
     * @return Una lista de objetos {@link SupplierDTO} con los datos básicos de las proveedores.
     */
    @Transactional(readOnly = true)
    public List<SupplierDTO> getSupplierBasicList(Long companyId, boolean visible) {
        try {
            return getSupplierBasicList(supplierRepository.findAllProjectedBasicByCompanyIdAndVisible(companyId, visible));
        } catch (Exception e) {
            logger.error("No se pudo obtener la lista basica de proveedores por empresa -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Obtiene una lista detallada (resumen) de proveedores visibles en el sistema.
     * <p>
     * Esta lista incluye información adicional además del ID y nombre,
     * como dirección, identificador de la sucursal y su nombre.
     *
     * @param visible Indica si se deben incluir solo las proveedores visibles.
     * @return Una lista de objetos {@link SupplierDTO} con datos resumidos de las proveedores.
     */
    @Transactional(readOnly = true)
    public List<SupplierDTO> getSupplierSummaryList(boolean visible) {
        try {
            return getSupplierSummaryList(supplierRepository.findAllProjectedSummaryByVisible(visible));
        } catch (Exception e) {
            logger.error("No se pudo obtener la lista detallada de proveedores -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Convierte una lista de objetos {@link SupplierProjection} en una lista detallada de {@link SupplierDTO},
     * mapeando los datos relevantes de los proveedores y sus respectivas sucursales.
     *
     * @param projectionList la lista de proyecciones de los proveedores a convertir.
     * @return una lista de objetos {@link SupplierDTO} construidos a partir de la lista de entrada.
     */
    private List<SupplierDTO> getSupplierSummaryList(List<SupplierProjection> projectionList) {
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

                    return SupplierDTO.builder()
                            .idSupplier(p.getIdSupplier())
                            .names(p.getNames())
                            .lastNames(p.getLastNames())
                            .identificationNumber(p.getIdentificationNumber())
                            .phone(p.getPhone())
                            .quantityProducts(p.getProductCount())
                            .branchDTO(branchDTO)
                            .build();
                })
                .toList();
    }

    /**
     * Obtiene una lista detallada (resumen) de proveedores por empresa y visibles en el sistema.
     * <p>
     * Esta lista incluye información adicional además del ID y nombre,
     * como dirección, identificador de la sucursal y su nombre.
     *
     * @param visible   Indica si se deben incluir solo las proveedores visibles.
     * @param companyId Identificador de la empresa.
     * @return Una lista de objetos {@link BranchDTO} con datos resumidos de las proveedores.
     */
    @Transactional(readOnly = true)
    public List<SupplierDTO> getSupplierSummaryList(Long companyId, boolean visible) {
        try {
            return getSupplierSummaryList(supplierRepository.findAllProjectedSummaryByCompanyIdAndVisible(companyId, visible));
        } catch (Exception e) {
            logger.error("No se pudo obtener la lista detallada de proveedores por empresa -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }


    /**
     * Inserta o actualiza un proveedor.
     *
     * @param supplierDTO un Objeto SupplierDTO que representa los datos del proveedor.
     * @return {@code true} si la operación fue exitosa, {@code false} en caso contrario.
     */
    @Transactional
    public boolean saveSupplier(SupplierDTO supplierDTO) {
        if (supplierDTO.getIdSupplier() == null) {
            return insertSupplier(supplierDTO);
        } else {
            return updateSupplier(supplierDTO);
        }
    }

    /**
     * Inserta un nuevo proveedor en la base de datos a partir del DTO proporcionado.
     *
     * @param supplierDTO el objeto DTO que contiene los datos del proveedor a insertar
     * @return {@code true} si la proveedor fue insertada correctamente (es decir, si se generó un ID); {@code false} en caso de error
     */
    @Transactional
    protected boolean insertSupplier(SupplierDTO supplierDTO) {
        try {
            SupplierEntity supplierEntity = supplierMapper.toEntity(supplierDTO);
            supplierRepository.save(supplierEntity);
            return supplierEntity.getIdSupplier() != null;
        } catch (Exception e) {
            logger.error("No se pudo insertar el proveedor -> {}.", e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza la información de un proveedor existente en la base de datos utilizando el DTO proporcionado.
     *
     * @param supplierDTO el objeto DTO con los datos actualizados del proveedor
     * @return {@code true} si la actualización fue exitosa (es decir, si el ID sigue presente); {@code false} en caso de error
     */
    @Transactional
    protected boolean updateSupplier(SupplierDTO supplierDTO) {
        try {
            SupplierEntity supplierEntity = supplierMapper.toEntity(supplierDTO);
            supplierRepository.save(supplierEntity);
            return true;
        } catch (Exception e) {
            logger.error("No se pudo actualizar el proveedor -> {}.", e.getMessage());
            return false;
        }
    }

    /**
     * Elimina una proveedor de la base de datos según los datos del DTO proporcionado.
     *
     * @param supplierDTO el objeto DTO que representa el proveedor a eliminar
     * @return {@code true} si el proveedor fue eliminada correctamente; {@code false} en caso de error
     */
    @Transactional
    public boolean deleteSupplier(SupplierDTO supplierDTO) {
        try {
            supplierRepository.delete(supplierMapper.toEntity(supplierDTO));
            return true;
        } catch (Exception e) {
            logger.error("No se pudo eliminar el proveedor -> {}.", e.getMessage());
            return false;
        }
    }

}
