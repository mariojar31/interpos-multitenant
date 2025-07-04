package com.interfija.masterposmultitenant.services.company;

import com.interfija.masterposmultitenant.dto.company.CompanyDTO;
import com.interfija.masterposmultitenant.entities.tenant.company.CompanyEntity;
import com.interfija.masterposmultitenant.mappers.company.CompanyMapper;
import com.interfija.masterposmultitenant.repository.company.CompanyRepository;
import com.interfija.masterposmultitenant.services.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Clase que gestiona las operaciones relacionadas con las empresas dentro del sistema.
 * Esta clase maneja las instancias de acceso a la base de datos para las empresas,
 * permitiendo realizar operaciones como obtener, crear y actualizar empresas.
 *
 * @author Steven Arzuza.
 */
@Service
public class CompanyService extends BaseService {

    /**
     * Objeto que maneja las operaciones de acceso a la base de datos para las empresas.
     */
    private final CompanyRepository companyRepository;

    /**
     * Mapper para convertir entre entidades {@link CompanyEntity} y {@link CompanyDTO} relacionados.
     */
    private final CompanyMapper companyMapper;

    /**
     * Constructor de la clase {@code CompanyService}.
     * Inicializa los componentes necesarios para el manejo de las empresas,
     * incluyendo la fuente de datos, el DAO de empresas y los datos maestros.
     */
    @Autowired
    public CompanyService(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        setLogger(CompanyService.class);
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    /**
     * Busca una empresa en la base de datos según los criterios proporcionados en {@code companyIndexDTO}.
     *
     * @return Un {@code Optional} que contiene la información de la empresa si se encuentra,
     * de lo contrario, un {@code Optional} vacío.
     */
    @Transactional(readOnly = true)
    public Optional<CompanyDTO> getCompany(Long idCompany) {
        try {
            return companyRepository.findById(idCompany)
                    .map(companyMapper::toDto);
        } catch (Exception e) {
            logger.error("No se pudo obtener la empresa -> {}.", e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Obtiene una lista básica de empresas visibles en el sistema.
     * <p>
     * Esta lista contiene únicamente los campos esenciales de la empresa:
     * ID y nombre.
     *
     * @param visible Indica si se deben incluir solo las empresas visibles.
     * @return Una lista de objetos {@link CompanyDTO} con los datos básicos de las empresas.
     */
    @Transactional(readOnly = true)
    public List<CompanyDTO> getCompanyBasicList(boolean visible) {
        try {
            return companyRepository.findAllProjectedBasicByVisible(visible)
                    .stream()
                    .map(p ->
                            CompanyDTO.builder()
                                    .idCompany(p.getIdCompany())
                                    .name(p.getName())
                                    .build()
                    )
                    .toList();
        } catch (Exception e) {
            logger.error("No se pudo obtener la lista basica de empresas -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Obtiene una lista detallada (resumen) de empresas visibles en el sistema.
     * <p>
     * Esta lista incluye información adicional además del ID y nombre,
     * como número de identificación y dirección.
     *
     * @param visible Indica si se deben incluir solo las empresas visibles.
     * @return Una lista de objetos {@link CompanyDTO} con datos resumidos de las empresas.
     */
    @Transactional(readOnly = true)
    public List<CompanyDTO> getCompanySummaryList(boolean visible) {
        try {
            return companyRepository.findAllProjectedSummaryByVisible(visible)
                    .stream()
                    .map(p ->
                            CompanyDTO.builder()
                                    .idCompany(p.getIdCompany())
                                    .name(p.getName())
                                    .identificationNumber(p.getIdentificationNumber())
                                    .address(p.getAddress())
                                    .build()
                    )
                    .toList();
        } catch (Exception e) {
            logger.error("No se pudo obtener la lista detallada de empresas -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Inserta o actualiza una empresa en la base de datos.
     *
     * @param companyDTO un Objeto CompanyDto que representa los datos de la empresa.
     * @return true si la empresa fue insertada o actualizada correctamente, en caso contrario, false.
     */
    @Transactional
    public boolean saveCompany(CompanyDTO companyDTO) {
        if (companyDTO.getIdCompany() == null) {
            return insertCompany(companyDTO);
        } else {
            return updateCompany(companyDTO);
        }
    }

    /**
     * Inserta una nueva empresa en la base de datos a partir del DTO proporcionado.
     *
     * @param companyDTO el objeto DTO que contiene los datos de la empresa a insertar
     * @return {@code true} si la empresa fue insertada correctamente (es decir, si se generó un ID); {@code false} en caso de error
     */
    @Transactional
    protected boolean insertCompany(CompanyDTO companyDTO) {
        try {
            CompanyEntity companyEntity = companyMapper.toEntity(companyDTO);
            companyRepository.save(companyEntity);
            return companyEntity.getIdCompany() != null;
        } catch (Exception e) {
            logger.error("No se pudo insertar la empresa -> {}.", e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza la información de una empresa existente en la base de datos utilizando el DTO proporcionado.
     *
     * @param companyDTO el objeto DTO con los datos actualizados de la empresa
     * @return {@code true} si la actualización fue exitosa (es decir, si el ID sigue presente); {@code false} en caso de error
     */
    @Transactional
    protected boolean updateCompany(CompanyDTO companyDTO) {
        try {
            CompanyEntity companyEntity = companyMapper.toEntity(companyDTO);
            companyRepository.save(companyEntity);
            return true;
        } catch (Exception e) {
            logger.error("No se pudo actualizar la empresa -> {}.", e.getMessage());
            return false;
        }
    }

    /**
     * Elimina una empresa de la base de datos según los datos del DTO proporcionado.
     *
     * @param companyDTO el objeto DTO que representa la empresa a eliminar
     * @return {@code true} si la empresa fue eliminada correctamente; {@code false} en caso de error
     */
    @Transactional
    public boolean deleteCompany(CompanyDTO companyDTO) {
        try {
            companyRepository.delete(companyMapper.toEntity(companyDTO));
            return true;
        } catch (Exception e) {
            logger.error("No se pudo eliminar la empresa -> {}.", e.getMessage());
            return false;
        }
    }

}
