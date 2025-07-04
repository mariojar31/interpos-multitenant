package com.interfija.masterposmultitenant.mappers.product;

import com.interfija.masterposmultitenant.dto.product.ProductDTO;
import com.interfija.masterposmultitenant.entities.tenant.product.ProductEntity;
import com.interfija.masterposmultitenant.mappers.base.BaseMapper;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import com.interfija.masterposmultitenant.mappers.category.CategoryMapper;
import com.interfija.masterposmultitenant.mappers.other.BarcodeTypeMapper;
import com.interfija.masterposmultitenant.mappers.other.TypeUnitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductMapper extends BaseMapper implements GenericMapper<ProductEntity, ProductDTO> {

    private final BarcodeTypeMapper barcodeTypeMapper;

    private final CategoryMapper categoryMapper;

    private final TypeUnitMapper typeUnitMapper;

    @Autowired
    public ProductMapper(BarcodeTypeMapper barcodeTypeMapper, CategoryMapper categoryMapper, TypeUnitMapper typeUnitMapper) {
        this.barcodeTypeMapper = barcodeTypeMapper;
        this.categoryMapper = categoryMapper;
        this.typeUnitMapper = typeUnitMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductDTO toDto(ProductEntity entity) {
        if (entity == null) {
            return null;
        }

        return ProductDTO.builder()
                .idProduct(entity.getIdProduct())
                .barcodeTypeDTO(barcodeTypeMapper.toDto(entity.getBarcodeTypeEntity()))
                .barcode(entity.getBarcode())
                .reference(entity.getReference())
                .name(entity.getName())
                .categoryDTO(categoryMapper.toDto(entity.getCategoryEntity()))
                .typeUnitDTO(typeUnitMapper.toDto(entity.getTypeUnitEntity()))
                .service(entity.getService())
                .variablePrice(entity.getVariablePrice())
                .bundle(entity.getBundle())
                .image(entity.getImage())
                .taxesList(new ArrayList<>())
                .branchesList(new ArrayList<>())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductEntity toEntity(ProductDTO dto) {
        if (dto == null) {
            return null;
        }

        return ProductEntity.builder()
                .idProduct(dto.getIdProduct())
                .barcodeTypeEntity(barcodeTypeMapper.toEntity(dto.getBarcodeTypeDTO()))
                .barcode(dto.getBarcode())
                .reference(dto.getReference())
                .name(dto.getName())
                .categoryEntity(categoryMapper.toEntity(dto.getCategoryDTO()))
                .typeUnitEntity(typeUnitMapper.toEntity(dto.getTypeUnitDTO()))
                .service(dto.isService())
                .variablePrice(dto.isVariablePrice())
                .bundle(dto.isBundle())
                .image(dto.getImage())
                .taxes(new ArrayList<>())
                .branches(new ArrayList<>())
                .build();
    }

    public void updateExistingEntity(ProductEntity entity, ProductDTO dto) {
        entity.setBarcodeTypeEntity(barcodeTypeMapper.toEntity(dto.getBarcodeTypeDTO()));
        entity.setBarcode(dto.getBarcode());
        entity.setReference(dto.getReference());
        entity.setName(dto.getName());
        entity.setCategoryEntity(categoryMapper.toEntity(dto.getCategoryDTO()));
        entity.setTypeUnitEntity(typeUnitMapper.toEntity(dto.getTypeUnitDTO()));
        entity.setService(dto.isService());
        entity.setVariablePrice(dto.isVariablePrice());
        entity.setBundle(dto.isBundle());
        entity.setImage(dto.getImage());
    }

}
