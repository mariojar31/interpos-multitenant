package com.interfija.masterposmultitenant.mappers.product;

import com.interfija.masterposmultitenant.dto.product.ProductTaxDTO;
import com.interfija.masterposmultitenant.entities.tenant.product.ProductEntity;
import com.interfija.masterposmultitenant.entities.tenant.product.ProductTaxEntity;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import com.interfija.masterposmultitenant.mappers.other.TypeTaxMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductTaxMapper implements GenericMapper<ProductTaxEntity, ProductTaxDTO> {

    private final TypeTaxMapper typeTaxMapper;

    @Autowired
    public ProductTaxMapper(TypeTaxMapper typeTaxMapper) {
        this.typeTaxMapper = typeTaxMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductTaxDTO toDto(ProductTaxEntity entity) {
        if (entity == null) {
            return null;
        }

        return ProductTaxDTO.builder()
                .idProductTax(entity.getIdProductTax())
                .typeTaxDTO(typeTaxMapper.toDto(entity.getTypeTaxEntity()))
                .productId(entity.getProductEntity().getIdProduct())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductTaxEntity toEntity(ProductTaxDTO dto) {
        if (dto == null) {
            return null;
        }

        ProductEntity productEntity = ProductEntity.builder()
                .idProduct(dto.getProductId())
                .build();

        return ProductTaxEntity.builder()
                .idProductTax(dto.getIdProductTax())
                .typeTaxEntity(typeTaxMapper.toEntity(dto.getTypeTaxDTO()))
                .productEntity(productEntity)
                .build();
    }

    public void updateExistingEntity(ProductTaxEntity entity, ProductTaxDTO dto) {

    }

}
