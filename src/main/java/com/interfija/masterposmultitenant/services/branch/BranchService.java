package com.interfija.masterposmultitenant.services.branch;

import com.interfija.masterposmultitenant.dto.branch.BranchDTO;
import com.interfija.masterposmultitenant.dto.company.CompanyDTO;
import com.interfija.masterposmultitenant.entities.tenant.branch.BranchEntity;
import com.interfija.masterposmultitenant.mappers.branch.BranchMapper;
import com.interfija.masterposmultitenant.repository.branch.BranchRepository;
import com.interfija.masterposmultitenant.repository.branch.projections.BranchProjection;
import com.interfija.masterposmultitenant.services.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Modelo que gestiona las sucursales de la aplicación.
 * Esta clase se encarga de acceder y manipular los datos de las sucursales almacenados en la base de datos.
 * Utiliza el patrón DAO para interactuar con la fuente de datos y acceder a la información de sucursal.
 *
 * @author Steven Arzuza.
 */
@Service
public class BranchService extends BaseService {

    /**
     * Objeto que maneja las operaciones de acceso a la base de datos para las sucursales.
     */
    private final BranchRepository branchRepository;

    /**
     * Mapper para convertir entre entidades {@link BranchEntity} y {@link BranchDTO} relacionados.
     */
    private final BranchMapper branchMapper;

    /**
     * Constructor que inicializa los objetos necesarios para la gestión de sucursales.
     * Inicializa la fuente de datos, el DAO de sucursales y los datos maestros.
     */
    @Autowired
    public BranchService(BranchRepository branchRepository, BranchMapper branchMapper) {
        setLogger(BranchService.class);
        this.branchRepository = branchRepository;
        this.branchMapper = branchMapper;
    }

    /**
     * Obtiene una sucursal registrada en el sistema.
     *
     * @return Un objeto BranchDTO que representan la sucursal registrada.
     */
    @Transactional(readOnly = true)
    public Optional<BranchDTO> getBranch(Long idBranch) {
        try {
            return branchRepository.findById(idBranch)
                    .map(branchMapper::toDto);
        } catch (Exception e) {
            logger.error("No se pudo obtener la sucursal -> {}.", e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Obtiene una lista básica de sucursales visibles en el sistema.
     * <p>
     * Esta lista contiene únicamente los campos esenciales de la empresa:
     * ID y nombre.
     *
     * @param visible Indica si se deben incluir solo las sucursales visibles.
     * @return Una lista de objetos {@link BranchDTO} con los datos básicos de las sucursales.
     */
    @Transactional(readOnly = true)
    public List<BranchDTO> getBranchBasicList(boolean visible) {
        try {
            return getBranchBasicList(branchRepository.findAllProjectedBasicByVisible(visible));
        } catch (Exception e) {
            logger.error("No se pudo obtener la lista basica de sucursales -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Convierte una lista de objetos {@link BranchProjection} en una lista básica de {@link BranchDTO},
     * mapeando los datos relevantes de sucursales y sus respectivas empresas.
     *
     * @param projectionList la lista de proyecciones de sucursales a convertir.
     * @return una lista de objetos {@link BranchDTO} construidos a partir de la lista de entrada.
     */
    private List<BranchDTO> getBranchBasicList(List<BranchProjection> projectionList) {
        return projectionList
                .stream()
                .map(p ->
                        BranchDTO.builder()
                                .idBranch(p.getIdBranch())
                                .name(p.getName())
                                .build()
                )
                .toList();
    }

    /**
     * Obtiene una lista básica de sucursales visibles en el sistema.
     * <p>
     * Esta lista contiene únicamente los campos esenciales de la empresa:
     * ID y nombre.
     *
     * @param visible   Indica si se deben incluir solo las sucursales visibles.
     * @param companyId Identificador de la empresa.
     * @return Una lista de objetos {@link BranchDTO} con los datos básicos de las sucursales.
     */
    @Transactional(readOnly = true)
    public List<BranchDTO> getBranchBasicList(Long companyId, boolean visible) {
        try {
            return getBranchBasicList(branchRepository.findAllProjectedBasicByCompanyIdAndVisible(companyId, visible));
        } catch (Exception e) {
            logger.error("No se pudo obtener la lista basica de sucursales por empresa -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Obtiene una lista detallada (resumen) de sucursales visibles en el sistema.
     * <p>
     * Esta lista incluye información adicional además del ID y nombre,
     * como dirección, identificador de la empresa y su nombre.
     *
     * @param visible Indica si se deben incluir solo las sucursales visibles.
     * @return Una lista de objetos {@link BranchDTO} con datos resumidos de las sucursales.
     */
    @Transactional(readOnly = true)
    public List<BranchDTO> getBranchSummaryList(boolean visible) {
        try {
            return getBranchSummaryList(branchRepository.findAllProjectedSummaryByVisible(visible));
        } catch (Exception e) {
            logger.error("No se pudo obtener la lista detallada de sucursales -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Convierte una lista de objetos {@link BranchProjection} en una lista detallada de {@link BranchDTO},
     * mapeando los datos relevantes de sucursales y sus respectivas empresas.
     *
     * @param projectionList la lista de proyecciones de sucursales a convertir.
     * @return una lista de objetos {@link BranchDTO} construidos a partir de la lista de entrada.
     */
    private List<BranchDTO> getBranchSummaryList(List<BranchProjection> projectionList) {
        return projectionList
                .stream()
                .map(p -> {
                    CompanyDTO companyDTO = CompanyDTO.builder()
                            .idCompany(p.getCompanyId())
                            .name(p.getCompanyName())
                            .build();

                    return BranchDTO.builder()
                            .idBranch(p.getIdBranch())
                            .name(p.getName())
                            .address(p.getAddress())
                            .companyDTO(companyDTO)
                            .build();
                })
                .toList();
    }

    /**
     * Obtiene una lista detallada (resumen) de sucursales por empresa y visibles en el sistema.
     * <p>
     * Esta lista incluye información adicional además del ID y nombre,
     * como dirección, identificador de la empresa y su nombre.
     *
     * @param visible   Indica si se deben incluir solo las sucursales visibles.
     * @param companyId Identificador de la empresa.
     * @return Una lista de objetos {@link BranchDTO} con datos resumidos de las sucursales.
     */
    @Transactional(readOnly = true)
    public List<BranchDTO> getBranchSummaryList(Long companyId, boolean visible) {
        try {
            return getBranchSummaryList(branchRepository.findAllProjectedSummaryByCompanyIdAndVisible(companyId, visible));
        } catch (Exception e) {
            logger.error("No se pudo obtener la lista detallada de sucursales por empresa -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Inserta o actualiza una sucursal en la base de datos.
     *
     * @param branchDTO un Objeto BranchDTO que representa los datos de la sucursal
     * @return true si la sucursal fue insertada o actualizada correctamente, en caso contrario, false.
     */
    @Transactional
    public boolean saveBranch(BranchDTO branchDTO) {
        if (branchDTO.getIdBranch() == null) {
            return insertBranch(branchDTO);
        } else {
            return updateBranch(branchDTO);
        }
    }

    /**
     * Inserta una nueva sucursal en la base de datos a partir del DTO proporcionado.
     *
     * @param branchDTO el objeto DTO que contiene los datos de la sucursal a insertar
     * @return {@code true} si la sucursal fue insertada correctamente (es decir, si se generó un ID); {@code false} en caso de error
     */
    @Transactional
    protected boolean insertBranch(BranchDTO branchDTO) {
        try {
            BranchEntity branchEntity = branchMapper.toEntity(branchDTO);
            branchRepository.save(branchEntity);
            return branchEntity.getIdBranch() != null;
        } catch (Exception e) {
            logger.error("No se pudo insertar la sucursal -> {}.", e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza la información de una sucursal existente en la base de datos utilizando el DTO proporcionado.
     *
     * @param branchDTO el objeto DTO con los datos actualizados de la sucursal
     * @return {@code true} si la actualización fue exitosa (es decir, si el ID sigue presente); {@code false} en caso de error
     */
    @Transactional
    protected boolean updateBranch(BranchDTO branchDTO) {
        try {
            BranchEntity branchEntity = branchMapper.toEntity(branchDTO);
            branchRepository.save(branchEntity);
            return true;
        } catch (Exception e) {
            logger.error("No se pudo actualizar la sucursal -> {}.", e.getMessage());
            return false;
        }
    }

    /**
     * Elimina una sucursal de la base de datos según los datos del DTO proporcionado.
     *
     * @param branchDTO el objeto DTO que representa la sucursal a eliminar
     * @return {@code true} si la sucursal fue eliminada correctamente; {@code false} en caso de error
     */
    @Transactional
    public boolean deleteBranch(BranchDTO branchDTO) {
        try {
            branchRepository.delete(branchMapper.toEntity(branchDTO));
            return true;
        } catch (Exception e) {
            logger.error("No se pudo eliminar la sucursal -> {}.", e.getMessage());
            return false;
        }
    }

}
