package com.interfija.masterposmultitenant.services.product;

import com.interfija.masterposmultitenant.dto.branch.BranchDTO;
import com.interfija.masterposmultitenant.dto.category.CategoryDTO;
import com.interfija.masterposmultitenant.dto.company.CompanyDTO;
import com.interfija.masterposmultitenant.dto.other.TypeUnitDTO;
import com.interfija.masterposmultitenant.dto.product.ProductBranchBatchDTO;
import com.interfija.masterposmultitenant.dto.product.ProductDTO;
import com.interfija.masterposmultitenant.dto.product.ProductBranchPriceDTO;
import com.interfija.masterposmultitenant.dto.product.ProductBranchDTO;
import com.interfija.masterposmultitenant.dto.product.ProductTaxDTO;
import com.interfija.masterposmultitenant.entities.tenant.product.ProductBranchBatchEntity;
import com.interfija.masterposmultitenant.entities.tenant.product.ProductEntity;
import com.interfija.masterposmultitenant.entities.tenant.product.ProductBranchPriceEntity;
import com.interfija.masterposmultitenant.entities.tenant.product.ProductBranchEntity;
import com.interfija.masterposmultitenant.entities.tenant.product.ProductTaxEntity;
import com.interfija.masterposmultitenant.mappers.product.ProductBranchBatchMapper;
import com.interfija.masterposmultitenant.mappers.product.ProductMapper;
import com.interfija.masterposmultitenant.mappers.product.ProductBranchPriceMapper;
import com.interfija.masterposmultitenant.mappers.product.ProductBranchMapper;
import com.interfija.masterposmultitenant.mappers.product.ProductTaxMapper;
import com.interfija.masterposmultitenant.repository.product.ProductRepository;
import com.interfija.masterposmultitenant.repository.product.projections.ProductProjection;
import com.interfija.masterposmultitenant.services.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Clase que gestiona las operaciones relacionadas con los productos dentro del sistema.
 * Esta clase maneja las instancias de acceso a la base de datos para los productos,
 * permitiendo realizar operaciones como obtener y actualizar productos asociados.
 *
 * @author Steven Arzuza.
 */
@Service
public class ProductService extends BaseService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final ProductBranchPriceMapper branchPriceMapper;

    private final ProductTaxMapper productTaxMapper;

    private final ProductBranchMapper productBranchMapper;

    private final ProductBranchBatchMapper branchBatchMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductMapper productMapper,
                          ProductBranchPriceMapper branchPriceMapper, ProductTaxMapper productTaxMapper,
                          ProductBranchMapper productBranchMapper, ProductBranchBatchMapper branchBatchMapper) {

        setLogger(ProductService.class);
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.branchPriceMapper = branchPriceMapper;
        this.productTaxMapper = productTaxMapper;
        this.productBranchMapper = productBranchMapper;
        this.branchBatchMapper = branchBatchMapper;
    }

    /**
     * Obtiene una producto registrad en el sistema.
     *
     * @return Un objeto EmployeeDTO que representan el producto registrada.
     */
    @Transactional(readOnly = true)
    public Optional<ProductDTO> getProduct(Long idProduct) {
        try {
            return productRepository.findById(idProduct)
                    .map(this::fullMappingProduct);
        } catch (Exception e) {
            logger.error("No se pudo obtener el producto por id -> {}", e.getMessage());
            return Optional.empty();
        }
    }

    private ProductDTO fullMappingProduct(ProductEntity productEntity) {
        ProductDTO productDTO = productMapper.toDto(productEntity);

        productDTO.setTaxesList(productTaxMapper.toDtoList(productEntity.getTaxes()));

        for (ProductBranchEntity productBranchEntity : productEntity.getBranches()) {
            ProductBranchDTO productBranchDTO = productBranchMapper.toDto(productBranchEntity);
            productBranchDTO.setPricesList(branchPriceMapper.toDtoList(productBranchEntity.getPrices()));
            productBranchDTO.setBatchesList(branchBatchMapper.toDtoList(productBranchEntity.getBatches()));
            productDTO.getBranchesList().add(productBranchDTO);
        }

        return productDTO;
    }

    /**
     * Obtiene una producto registrad en el sistema.
     *
     * @return Un objeto EmployeeDTO que representan el producto registrada.
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> getProductWithDefaultPrice(Long idBranch) {
        return productRepository.findAllByBranchId(idBranch, true).stream()
                .map(productEntity -> {
                    ProductBranchEntity productBranchEntity = productEntity.getBranches().getFirst();
                    productBranchEntity.getPrices().removeIf(p -> !p.getDefaultPrice());
                    productBranchEntity.setBatches(Collections.emptyList());
                    return fullMappingProduct(productEntity);
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getProductByCompanyAndFiltersList(long companyId, boolean visible, boolean expired,
                                                              boolean inventoryControl, LocalDate expirationDate,
                                                              BigDecimal minUni, BigDecimal minKg, BigDecimal minL,
                                                              BigDecimal minM) {
        try {
            List<ProductProjection> projections = productRepository.findAllByCompanyIdAndFilters(
                    companyId, visible, expired, inventoryControl, expirationDate, minUni, minKg, minL, minM
            );

            return projections.stream()
                    .map(p -> {
                        CategoryDTO categoryDTO = CategoryDTO.builder()
                                .idCategory(p.getCategoryId())
                                .name(p.getCategoryName())
                                .build();

                        CompanyDTO companyDTO = CompanyDTO.builder()
                                .idCompany(p.getCompanyId())
                                .name(p.getCompanyName())
                                .build();

                        BranchDTO branchDTO = BranchDTO.builder()
                                .idBranch(p.getBranchId())
                                .name(p.getBranchName())
                                .companyDTO(companyDTO)
                                .build();

                        ProductBranchDTO productBranchDTO = ProductBranchDTO.builder()
                                .branchDTO(branchDTO)
                                .build();

                        TypeUnitDTO typeUnitDTO = TypeUnitDTO.builder()
                                .idTypeUnit(p.getTypeUnitId())
                                .name(p.getTypeUnitName())
                                .abbreviation(p.getTypeUnitAbbreviation())
                                .baseValue(p.getTypeUnitBaseValue())
                                .build();

                        return ProductDTO.builder()
                                .idProduct(p.getIdProduct())
                                .name(p.getName())
                                .reference(p.getReference())
                                .barcode(p.getBarcode())
                                .categoryDTO(categoryDTO)
                                .typeUnitDTO(typeUnitDTO)
                                .branchesList(List.of(productBranchDTO))
                                .build();
                    })
                    .toList();
        } catch (Exception e) {
            logger.error("No se pudo obtener la lista de productos por empresa y filtros -> {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Obtiene una lista de todos los productos registrados en el sistema.
     *
     * @return Una lista de objetos ProductDTO que representan los productos registrados.
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> getProductByCompanyList(long companyId, boolean visible, boolean fullMapping) {
        try {
            if (fullMapping) {
                return productRepository.findAllByCompanyId(companyId)
                        .stream()
                        .map(productMapper::toDto)
                        .toList();
            } else {
                return productRepository.findAllByCompanyId(companyId)
                        .stream()
                        .map(this::fullMappingProduct)
                        .toList();
            }
        } catch (Exception e) {
            logger.error("No se pudo obtener la lista de productos por empresa -> {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Obtiene una lista de todos los productos registrados en el sistema.
     *
     * @return Una lista de objetos ProductDTO que representan los productos registrados.
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> getProductByBranchList(long branchId, boolean visible, boolean fullMapping) {
        try {
            if (fullMapping) {
                return productRepository.findAllByBranchId(branchId, visible)
                        .stream()
                        .map(productMapper::toDto)
                        .toList();
            } else {
                return productRepository.findAllByBranchId(branchId, visible)
                        .stream()
                        .map(this::fullMappingProduct)
                        .toList();
            }
        } catch (Exception e) {
            logger.error("No se pudo obtener la lista de productos por sucursal -> {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Válida y filtra los productos de la lista, asegurándose de que cada producto
     * tenga datos válidos para stock, precios, impuestos y proveedores.
     *
     * @param productsList Lista de productos a validar y filtrar.
     * @return Lista de productos válidos que tienen todos los datos necesarios.
     */
    private List<ProductDTO> validateAndFilterProductData(List<ProductDTO> productsList) {
        Map<String, Predicate<ProductDTO>> validations = Map.of(
                "no tiene sucursales asignadas", product -> !product.getBranchesList().isEmpty(),
                "no tiene impuestos asignados", product -> !product.getTaxesList().isEmpty()
        );

        return validateAndFilterData(productsList, ProductDTO::getName, validations);
    }

    @Transactional(readOnly = true)
    public ProductValidationError validateProduct(String name, String reference, String barcode, Long idProduct) {
        if (productRepository.existsByNameExcludingId(name, idProduct)) {
            return ProductValidationError.NAME_EXISTS;
        }
        if (productRepository.existsByReferenceExcludingId(reference, idProduct)) {
            return ProductValidationError.REFERENCE_EXISTS;
        }
        if (productRepository.existsByBarcodeExcludingId(barcode, idProduct)) {
            return ProductValidationError.BARCODE_EXISTS;
        }
        return ProductValidationError.NONE;
    }

    /**
     * Inserta o actualiza un producto en la base de datos.
     *
     * @param productDTO el objeto {@code ProductDTO} que representa los datos del producto.
     * @return {@code true} si la inserción fue exitosa, {@code false} en caso contrario.
     */
    @Transactional
    public boolean saveProduct(ProductDTO productDTO) {
        if (productDTO.getIdProduct() == null) {
            return insertProduct(productDTO);
        } else {
            return updateProduct(productDTO);
        }
    }

    /**
     * Inserta un nuevo producto en la base de datos a partir del DTO proporcionado.
     *
     * @param productDTO el objeto DTO que contiene los datos del producto a insertar
     * @return {@code true} si la producto fue insertada correctamente (es decir, si se generó un ID); {@code false} en caso de error
     */
    @Transactional
    public boolean insertProduct(ProductDTO productDTO) {
        try {
            ProductEntity productEntity = productMapper.toEntity(productDTO);
            mapAndAddEntities(productDTO.getTaxesList(), productTaxMapper::toEntity, productEntity::addTax);
            insertProductBranches(productDTO.getBranchesList(), productEntity::addBranch);

            productRepository.save(productEntity);
            return productEntity.getIdProduct() != null;
        } catch (Exception e) {
            logger.error("No se pudo insertar el producto -> {}.", e.getMessage());
            return false;
        }
    }

    private void insertProductBranches(List<ProductBranchDTO> dtoList, Consumer<ProductBranchEntity> addProductBranch) {
        for (ProductBranchDTO productBranchDTO : dtoList) {
            ProductBranchEntity productBranchEntity = productBranchMapper.toEntity(productBranchDTO);
            mapAndAddEntities(productBranchDTO.getPricesList(), branchPriceMapper::toEntity, productBranchEntity::addPrice);
            mapAndAddEntities(productBranchDTO.getBatchesList(), branchBatchMapper::toEntity, productBranchEntity::addBatch);
            addProductBranch.accept(productBranchEntity);
        }
    }

    /**
     * Actualiza la información de un producto existente en la base de datos utilizando el DTO proporcionado.
     *
     * @param productDTO el objeto DTO con los datos actualizados del producto
     * @return {@code true} si la actualización fue exitosa (es decir, si el ID sigue presente); {@code false} en caso de error
     */
    @Transactional
    public boolean updateProduct(ProductDTO productDTO) {
        try {
            Optional<ProductEntity> optionalProductEntity = productRepository.findById(productDTO.getIdProduct());
            if (optionalProductEntity.isEmpty()) {
                return false;
            }

            ProductEntity productEntity = optionalProductEntity.get();
            productMapper.updateExistingEntity(productEntity, productDTO);

            updateChildEntities(productEntity.getTaxes(), productDTO.getTaxesList(), ProductTaxDTO::getIdProductTax,
                    ProductTaxEntity::getIdProductTax, productTaxMapper::updateExistingEntity, productTaxMapper::toEntity,
                    productEntity::removeTax, productEntity::addTax);

            updateProductBranches(productDTO.getBranchesList(), productEntity);

            productRepository.save(productEntity);
            return true;
        } catch (Exception e) {
            logger.error("No se pudo actualizar el producto -> {}.", e.getMessage());
            return false;
        }
    }

    private void updateProductBranches(List<ProductBranchDTO> dtoList, ProductEntity productEntity) {
        Map<Long, ProductBranchDTO> dtoMap = dtoList.stream()
                .filter(dto -> dto.getIdProductBranch() != null)
                .collect(Collectors.toMap(ProductBranchDTO::getIdProductBranch, dto -> dto));

        // Actualizar entidades internas de las sucursales existentes
        for (ProductBranchEntity entity : productEntity.getBranches()) {
            ProductBranchDTO dto = dtoMap.get(entity.getIdProductBranch());
            if (dto != null) {
                updateChildEntities(entity.getPrices(), dto.getPricesList(), ProductBranchPriceDTO::getIdProductBranchPrice,
                        ProductBranchPriceEntity::getIdProductBranchPrice, branchPriceMapper::updateExistingEntity,
                        branchPriceMapper::toEntity, entity::removePrice, entity::addPrice);

                updateChildEntities(entity.getBatches(), dto.getBatchesList(), ProductBranchBatchDTO::getIdProductBranchBatch,
                        ProductBranchBatchEntity::getIdProductBranchBatch, branchBatchMapper::updateExistingEntity,
                        branchBatchMapper::toEntity, entity::removeBatch, entity::addBatch);
            }
        }

        // Crear nuevas entidades de sucursales con sus entidades internas
        List<ProductBranchEntity> newEntityList = new ArrayList<>();
        insertProductBranches(dtoList, newEntityList::add);

        // Actualizar sucursales de los productos
        updateChildEntities(productEntity.getBranches(), newEntityList, ProductBranchEntity::getIdProductBranch,
                productBranchMapper::updateExistingEntity, productEntity::removeBranch, productEntity::addBranch);
    }

    /**
     * Borrar un producto en la base de datos.
     *
     * @return true si el producto fue eliminado correctamente, en caso contrario, false.
     */
    @Transactional
    public boolean deleteProduct(ProductDTO productDTO) {
        try {
            productRepository.delete(productMapper.toEntity(productDTO));
            return true;
        } catch (Exception e) {
            logger.error("No se pudo eliminar el producto -> {}.", e.getMessage());
            return false;
        }
    }

}
