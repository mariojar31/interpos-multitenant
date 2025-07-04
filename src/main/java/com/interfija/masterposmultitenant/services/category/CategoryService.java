package com.interfija.masterposmultitenant.services.category;

import com.interfija.masterposmultitenant.dto.branch.BranchDTO;
import com.interfija.masterposmultitenant.dto.category.CategoryDTO;
import com.interfija.masterposmultitenant.dto.company.CompanyDTO;
import com.interfija.masterposmultitenant.entities.tenant.category.CategoryEntity;
import com.interfija.masterposmultitenant.mappers.category.CategoryMapper;
import com.interfija.masterposmultitenant.repository.category.CategoryRepository;
import com.interfija.masterposmultitenant.repository.category.projections.CategoryProjection;
import com.interfija.masterposmultitenant.services.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Clase que gestiona las operaciones relacionadas con las categorías dentro del sistema.
 * Esta clase maneja las instancias de acceso a la base de datos para las categorías,
 * permitiendo realizar operaciones como obtener, crear y actualizar categorías.
 *
 * @author Steven Arzuza.
 */
@Service
public class CategoryService extends BaseService {

    /**
     * Objeto que maneja las operaciones de acceso a la base de datos para categorías.
     */
    private final CategoryRepository categoryRepository;

    /**
     * Mapper para convertir entre entidades {@link CategoryEntity} y {@link CategoryDTO} relacionados.
     */
    private final CategoryMapper categoryMapper;

    /**
     * Constructor que inicializa los objetos necesarios para la gestión de categorías.
     * Inicializa la fuente de datos, el DAO de categorías y los datos maestros.
     */
    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        setLogger(CategoryService.class);
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    /**
     * Obtiene una categoría registrada en el sistema.
     *
     * @return Un objeto CategoryDTO que representan la categoría registrada.
     */
    @Transactional(readOnly = true)
    public Optional<CategoryDTO> getCategory(Long idCategory) {
        try {
            return categoryRepository.findById(idCategory)
                    .map(categoryMapper::toDto);
        } catch (Exception e) {
            logger.error("No se pudo obtener la categoría -> {}.", e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Obtiene una lista básica de categorías visibles en el sistema.
     * <p>
     * Esta lista contiene únicamente los campos esenciales de la empresa:
     * ID y nombre.
     *
     * @param visible Indica si se deben incluir solo las categorías visibles.
     * @return Una lista de objetos {@link CategoryDTO} con los datos básicos de las categorías.
     */
    @Transactional(readOnly = true)
    public List<CategoryDTO> getCategoryBasicList(boolean visible) {
        try {
            return getCategoryBasicList(categoryRepository.findAllProjectedBasicByVisible(visible));
        } catch (Exception e) {
            logger.error("No se pudo obtener la lista basica de categorías -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Convierte una lista de objetos {@link CategoryProjection} en una lista básica de {@link CategoryDTO},
     * mapeando los datos relevantes del empleado.
     *
     * @param projectionList la lista de proyecciones de categorías a convertir.
     * @return una lista de objetos {@link CategoryDTO} construidos a partir de la lista de entrada.
     */
    private List<CategoryDTO> getCategoryBasicList(List<CategoryProjection> projectionList) {
        return projectionList
                .stream()
                .map(p -> {
                            CategoryDTO parentDTO;
                            if (p.getParentId() != null) {
                                parentDTO = CategoryDTO.builder()
                                        .idCategory(p.getParentId())
                                        .name(p.getParentName())
                                        .build();
                            } else {
                                parentDTO = null;
                            }

                            return CategoryDTO.builder()
                                    .idCategory(p.getIdCategory())
                                    .name(p.getName())
                                    .parentDTO(parentDTO)
                                    .build();
                        }
                )
                .toList();
    }

    /**
     * Obtiene una lista básica de categorías visibles en el sistema.
     * <p>
     * Esta lista contiene únicamente los campos esenciales del empleado:
     * ID y nombre.
     *
     * @param visible   Indica si se deben incluir solo las categorías visibles.
     * @param companyId Identificador de la empresa.
     * @return Una lista de objetos {@link CategoryDTO} con los datos básicos de las categorías.
     */
    @Transactional(readOnly = true)
    public List<CategoryDTO> getCategoryBasicList(Long companyId, boolean visible) {
        try {
            return getCategoryBasicList(categoryRepository.findAllProjectedBasicByCompanyIdAndVisible(companyId, visible));
        } catch (Exception e) {
            logger.error("No se pudo obtener la lista basica de categorías por empresa -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Obtiene una lista detallada (resumen) de categorías visibles en el sistema.
     * <p>
     * Esta lista incluye información adicional además del ID y nombre,
     * como dirección, identificador de la sucursal y su nombre.
     *
     * @param visible Indica si se deben incluir solo las categorías visibles.
     * @return Una lista de objetos {@link CategoryDTO} con datos resumidos de las categorías.
     */
    @Transactional(readOnly = true)
    public List<CategoryDTO> getCategorySummaryList(boolean visible) {
        try {
            return getCategorySummaryList(categoryRepository.findAllProjectedSummaryByVisible(visible));
        } catch (Exception e) {
            logger.error("No se pudo obtener la lista detallada de categorías -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Convierte una lista de objetos {@link CategoryProjection} en una lista detallada de {@link CategoryDTO},
     * mapeando los datos relevantes de los categorías y sus respectivas sucursales.
     *
     * @param projectionList la lista de proyecciones de los categorías a convertir.
     * @return una lista de objetos {@link CategoryDTO} construidos a partir de la lista de entrada.
     */
    private List<CategoryDTO> getCategorySummaryList(List<CategoryProjection> projectionList) {
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

                    CategoryDTO parentDTO = CategoryDTO.builder()
                            .idCategory(p.getParentId())
                            .name(p.getParentName())
                            .build();

                    return CategoryDTO.builder()
                            .idCategory(p.getIdCategory())
                            .name(p.getName())
                            .code(p.getCode())
                            .quantityProducts(p.getProductCount())
                            .quantityChildren(p.getChildrenCount())
                            .branchDTO(branchDTO)
                            .parentDTO(parentDTO)
                            .build();
                })
                .toList();
    }

    /**
     * Obtiene una lista detallada (resumen) de categorías por empresa y visibles en el sistema.
     * <p>
     * Esta lista incluye información adicional además del ID y nombre,
     * como dirección, identificador de la sucursal y su nombre.
     *
     * @param visible   Indica si se deben incluir solo las categorías visibles.
     * @param companyId Identificador de la empresa.
     * @return Una lista de objetos {@link BranchDTO} con datos resumidos de las categorías.
     */
    @Transactional(readOnly = true)
    public List<CategoryDTO> getCategorySummaryList(Long companyId, boolean visible) {
        try {
            return getCategorySummaryList(categoryRepository.findAllProjectedSummaryByCompanyIdAndVisible(companyId, visible));
        } catch (Exception e) {
            logger.error("No se pudo obtener la lista detallada de categorías por empresa -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Inserta o actualiza una categoría en la base de datos.
     *
     * @param categoryDTO un Objeto CategoryDTO que representa los datos de la categoría.
     * @return {@code true} si la operación fue exitosa, {@code false} en caso contrario.
     */
    @Transactional
    public boolean saveCategory(CategoryDTO categoryDTO) {
        if (categoryDTO.getIdCategory() == null) {
            return insertCategory(categoryDTO);
        } else {
            return updateCategory(categoryDTO);
        }
    }

    /**
     * Inserta una nueva categoría en la base de datos a partir del DTO proporcionado.
     *
     * @param categoryDTO el objeto DTO que contiene los datos de la categoría a insertar
     * @return {@code true} si la categoría fue insertada correctamente (es decir, si se generó un ID); {@code false} en caso de error
     */
    @Transactional
    protected boolean insertCategory(CategoryDTO categoryDTO) {
        try {
            CategoryEntity categoryEntity = categoryMapper.toEntity(categoryDTO);
            categoryRepository.save(categoryEntity);
            return categoryEntity.getIdCategory() != null;
        } catch (Exception e) {
            logger.error("No se pudo insertar la categoría -> {}.", e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza la información de una categoría existente en la base de datos utilizando el DTO proporcionado.
     *
     * @param categoryDTO el objeto DTO con los datos actualizados de la categoría
     * @return {@code true} si la actualización fue exitosa (es decir, si el ID sigue presente); {@code false} en caso de error
     */
    @Transactional
    protected boolean updateCategory(CategoryDTO categoryDTO) {
        try {
            CategoryEntity categoryEntity = categoryMapper.toEntity(categoryDTO);
            categoryRepository.save(categoryEntity);
            return true;
        } catch (Exception e) {
            logger.error("No se pudo actualizar la categoría -> {}.", e.getMessage());
            return false;
        }
    }

    /**
     * Elimina una categoría de la base de datos según los datos del DTO proporcionado.
     *
     * @param categoryDTO el objeto DTO que representa la categoría a eliminar
     * @return {@code true} si la categoría fue eliminada correctamente; {@code false} en caso de error
     */
    @Transactional
    public boolean deleteCategory(CategoryDTO categoryDTO) {
        try {
            categoryRepository.delete(categoryMapper.toEntity(categoryDTO));
            return true;
        } catch (Exception e) {
            logger.error("No se pudo eliminar la categoría -> {}.", e.getMessage());
            return false;
        }
    }

}
