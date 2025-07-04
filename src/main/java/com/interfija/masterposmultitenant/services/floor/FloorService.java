package com.interfija.masterposmultitenant.services.floor;

import com.interfija.masterposmultitenant.dto.branch.BranchDTO;
import com.interfija.masterposmultitenant.dto.company.CompanyDTO;
import com.interfija.masterposmultitenant.dto.floor.FloorDTO;
import com.interfija.masterposmultitenant.dto.floor.TableDTO;
import com.interfija.masterposmultitenant.dto.floor.TerminalDTO;
import com.interfija.masterposmultitenant.entities.tenant.floor.FloorEntity;
import com.interfija.masterposmultitenant.entities.tenant.floor.TableEntity;
import com.interfija.masterposmultitenant.entities.tenant.floor.TerminalEntity;
import com.interfija.masterposmultitenant.mappers.floor.FloorMapper;
import com.interfija.masterposmultitenant.mappers.floor.TableMapper;
import com.interfija.masterposmultitenant.mappers.floor.TerminalMapper;
import com.interfija.masterposmultitenant.repository.floor.FloorRepository;
import com.interfija.masterposmultitenant.repository.floor.TableRepository;
import com.interfija.masterposmultitenant.repository.floor.TerminalRepository;
import com.interfija.masterposmultitenant.repository.floor.projections.FloorProjection;
import com.interfija.masterposmultitenant.services.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Modelo que gestiona los pisos de la aplicación.
 * Esta clase se encarga de acceder y manipular los datos de los pisos almacenados en la base de datos.
 * Utiliza el patrón DAO para interactuar con la fuente de datos y acceder a la información de piso.
 *
 * @author Steven Arzuza.
 */
@Service
public class FloorService extends BaseService {

    /**
     * Objeto que maneja las operaciones de acceso a la base de datos para los pisos.
     */
    private final FloorRepository floorRepository;

    /**
     * Mapper para convertir entre entidades {@link FloorEntity} y {@link FloorDTO} relacionados.
     */
    private final FloorMapper floorMapper;

    /**
     * Objeto que maneja las operaciones de acceso a la base de datos para las mesas.
     */
    private final TableRepository tableRepository;

    /**
     * Mapper para convertir entre entidades {@link TableEntity} y {@link TableDTO} relacionados.
     */
    private final TableMapper tableMapper;

    /**
     * Objeto que maneja las operaciones de acceso a la base de datos para las terminales.
     */
    private final TerminalRepository terminalRepository;

    /**
     * Mapper para convertir entre entidades {@link TerminalEntity} y {@link TerminalDTO} relacionados.
     */
    private final TerminalMapper terminalMapper;

    /**
     * Constructor de la clase {@code FloorModel}.
     * Inicializa los componentes necesarios para el manejo de los pisos,
     * incluyendo la fuente de datos, el DAO de pisos y los datos maestros.
     */
    @Autowired
    public FloorService(FloorRepository floorRepository, FloorMapper floorMapper,
                        TableRepository tableRepository, TableMapper tableMapper,
                        TerminalRepository terminalRepository, TerminalMapper terminalMapper) {
        setLogger(FloorService.class);
        this.floorRepository = floorRepository;
        this.floorMapper = floorMapper;
        this.tableRepository = tableRepository;
        this.tableMapper = tableMapper;
        this.terminalRepository = terminalRepository;
        this.terminalMapper = terminalMapper;
    }

    /**
     * Obtiene una piso registrada en el sistema.
     *
     * @return Un objeto FloorDTO que representan la piso registrada.
     */
    @Transactional(readOnly = true)
    public Optional<FloorDTO> getFloorBasic(Long idFloor) {
        try {
            return floorRepository.findById(idFloor)
                    .map(floorMapper::toDto);
        } catch (Exception e) {
            logger.error("No se pudo obtener los datos básicos del piso piso -> {}.", e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Obtiene una piso registrada en el sistema.
     *
     * @return Un objeto FloorDTO que representan la piso registrada.
     */
    @Transactional(readOnly = true)
    public Optional<FloorDTO> getFloorFull(Long idFloor) {
        try {
            return floorRepository.findById(idFloor)
                    .map(floorEntity -> {
                        FloorDTO floorDTO = floorMapper.toDto(floorEntity);
                        floorDTO.setTerminalsList(terminalMapper.toDtoList(floorEntity.getTerminals()));
                        floorDTO.setTablesList(tableMapper.toDtoList(floorEntity.getTables()));
                        return floorDTO;
                    });
        } catch (Exception e) {
            logger.error("No se pudo obtener los datos completos del piso -> {}.", e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Obtiene una lista básica de pisos visibles en el sistema.
     * <p>
     * Esta lista contiene únicamente los campos esenciales de la sucursal:
     * ID y nombre.
     *
     * @param visible Indica si se deben incluir solo las pisos visibles.
     * @return Una lista de objetos {@link FloorDTO} con los datos básicos de las pisos.
     */
    @Transactional(readOnly = true)
    public List<FloorDTO> getFloorBasicList(boolean visible) {
        try {
            return getFloorBasicList(floorRepository.findAllProjectedBasicByVisible(visible));
        } catch (Exception e) {
            logger.error("No se pudo obtener la lista basica de pisos -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Convierte una lista de objetos {@link FloorProjection} en una lista básica de {@link FloorDTO},
     * mapeando los datos relevantes de pisos.
     *
     * @param projectionList la lista de proyecciones de pisos a convertir.
     * @return una lista de objetos {@link FloorDTO} construidos a partir de la lista de entrada.
     */
    private List<FloorDTO> getFloorBasicList(List<FloorProjection> projectionList) {
        return projectionList
                .stream()
                .map(p ->
                        FloorDTO.builder()
                                .idFloor(p.getIdFloor())
                                .name(p.getName())
                                .build()
                )
                .toList();
    }

    /**
     * Obtiene una lista básica de pisos visibles en el sistema.
     * <p>
     * Esta lista contiene únicamente los campos esenciales de la sucursal:
     * ID y nombre.
     *
     * @param visible  Indica si se deben incluir solo las pisos visibles.
     * @param branchId Identificador de la sucursal.
     * @return Una lista de objetos {@link FloorDTO} con los datos básicos de las pisos.
     */
    @Transactional(readOnly = true)
    public List<FloorDTO> getFloorBasicList(Long branchId, boolean visible) {
        try {
            return getFloorBasicList(floorRepository.findAllProjectedBasicByBranchIdAndVisible(branchId, visible));
        } catch (Exception e) {
            logger.error("No se pudo obtener la lista basica de pisos por sucursal -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Obtiene una lista detallada (resumen) de pisos visibles en el sistema.
     * <p>
     * Esta lista incluye información adicional además del ID y nombre,
     * como dirección, identificador de la sucursal y su nombre.
     *
     * @param visible Indica si se deben incluir solo las pisos visibles.
     * @return Una lista de objetos {@link FloorDTO} con datos resumidos de las pisos.
     */
    @Transactional(readOnly = true)
    public List<FloorDTO> getFloorSummaryList(boolean visible) {
        try {
            return getFloorSummaryList(floorRepository.findAllProjectedSummaryByVisible(visible));
        } catch (Exception e) {
            logger.error("No se pudo obtener la lista detallada de pisos -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Convierte una lista de objetos {@link FloorProjection} en una lista detallada de {@link FloorDTO},
     * mapeando los datos relevantes de los pisos y sus respectivas pisos.
     *
     * @param projectionList la lista de proyecciones de los pisos a convertir.
     * @return una lista de objetos {@link FloorDTO} construidos a partir de la lista de entrada.
     */
    private List<FloorDTO> getFloorSummaryList(List<FloorProjection> projectionList) {
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

                    return FloorDTO.builder()
                            .idFloor(p.getIdFloor())
                            .name(p.getName())
                            .branchDTO(branchDTO)
                            .build();
                })
                .toList();
    }

    /**
     * Obtiene una lista detallada (resumen) de pisos por sucursal y visibles en el sistema.
     * <p>
     * Esta lista incluye información adicional además del ID y nombre,
     * como dirección, identificador de la sucursal y su nombre.
     *
     * @param visible  Indica si se deben incluir solo las pisos visibles.
     * @param branchId Identificador de la sucursal.
     * @return Una lista de objetos {@link BranchDTO} con datos resumidos de las pisos.
     */
    @Transactional(readOnly = true)
    public List<FloorDTO> getFloorSummaryList(Long branchId, boolean visible) {
        try {
            return getFloorSummaryList(floorRepository.findAllProjectedSummaryByBranchIdAndVisible(branchId, visible));
        } catch (Exception e) {
            logger.error("No se pudo obtener la lista detallada de pisos por sucursal -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Inserta o actualiza un piso en la base de datos.
     *
     * @param floorDTO el objeto {@code FloorDTO} que representa los datos del piso.
     * @return {@code true} si la inserción fue exitosa, {@code false} en caso contrario.
     */
    @Transactional
    public boolean saveFloor(FloorDTO floorDTO) {
        if (floorDTO.getIdFloor() == null) {
            return insertFloor(floorDTO);
        } else {
            return updateFloor(floorDTO);
        }
    }

    /**
     * Inserta un nuevo piso en la base de datos a partir del DTO proporcionado.
     *
     * @param floorDTO el objeto DTO que contiene los datos del piso a insertar
     * @return {@code true} si la piso fue insertada correctamente (es decir, si se generó un ID); {@code false} en caso de error
     */
    @Transactional
    protected boolean insertFloor(FloorDTO floorDTO) {
        try {
            FloorEntity floorEntity = floorMapper.toEntity(floorDTO);
            mapAndAddEntities(floorDTO.getTerminalsList(), terminalMapper::toEntity, floorEntity::addTerminal);
            mapAndAddEntities(floorDTO.getTablesList(), tableMapper::toEntity, floorEntity::addTable);
            floorRepository.save(floorEntity);
            return floorEntity.getIdFloor() != null;
        } catch (Exception e) {
            logger.error("No se pudo insertar el piso -> {}.", e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza la información de un piso existente en la base de datos utilizando el DTO proporcionado.
     *
     * @param floorDTO el objeto DTO con los datos actualizados del piso
     * @return {@code true} si la actualización fue exitosa (es decir, si el ID sigue presente); {@code false} en caso de error
     */
    @Transactional
    protected boolean updateFloor(FloorDTO floorDTO) {
        try {
            Optional<FloorEntity> optionalFloorEntity = floorRepository.findById(floorDTO.getIdFloor());
            if (optionalFloorEntity.isEmpty()) {
                return false;
            }

            FloorEntity floorEntity = optionalFloorEntity.get();

            floorMapper.updateExistingEntity(floorEntity, floorDTO);
            updateChildEntities(floorEntity.getTerminals(), floorDTO.getTerminalsList(), TerminalDTO::getIdTerminal,
                    TerminalEntity::getIdTerminal, terminalMapper::updateExistingEntity, terminalMapper::toEntity,
                    floorEntity::removeTerminal, floorEntity::addTerminal);

            updateChildEntities(floorEntity.getTables(), floorDTO.getTablesList(), TableDTO::getIdTable,
                    TableEntity::getIdTable, tableMapper::updateExistingEntity, tableMapper::toEntity,
                    floorEntity::removeTable, floorEntity::addTable);

            floorRepository.save(floorEntity);
            return true;
        } catch (Exception e) {
            logger.error("No se pudo actualizar el piso -> {}.", e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un piso de la base de datos según los datos del DTO proporcionado.
     *
     * @param floorDTO el objeto DTO que representa el piso a eliminar
     * @return {@code true} si la piso fue eliminada correctamente; {@code false} en caso de error
     */
    @Transactional
    public boolean deleteFloor(FloorDTO floorDTO) {
        try {
            floorRepository.delete(floorMapper.toEntity(floorDTO));
            return true;
        } catch (Exception e) {
            logger.error("No se pudo eliminar el piso -> {}.", e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene una terminal registrada en el sistema.
     *
     * @return Un objeto TerminalDTO que representan la terminal registrada.
     */
    @Transactional(readOnly = true)
    public Optional<TerminalDTO> getTerminalByName(String name) {
        try {
            return terminalRepository.findByName(name)
                    .map(terminalMapper::toDto);
        } catch (Exception e) {
            logger.error("No se pudo obtener la terminal por nombre -> {}.", e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Obtiene una lista de las terminales registradas en el sistema.
     *
     * @return Una lista de objetos TerminalDTO que representan las terminales registradas.
     */
    @Transactional(readOnly = true)
    public List<TerminalDTO> getTerminalsList(long floorId, boolean visible, boolean fullMapping) {
        return terminalRepository.findByFloorId(floorId, visible)
                .stream().map(terminalMapper::toDto)
                .toList();
    }

    /**
     * Obtiene una lista de las mesas registradas en el sistema.
     *
     * @return Una lista de objetos TableDTO que representan las mesas registradas.
     */
    @Transactional(readOnly = true)
    public List<TableDTO> getTablesList(long floorId, boolean visible) {
        List<TableEntity> tableList = tableRepository.findByFloorId(floorId, visible);
        return tableMapper.toDtoList(tableList);
    }

}
