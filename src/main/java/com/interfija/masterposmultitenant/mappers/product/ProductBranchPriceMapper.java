package com.interfija.masterposmultitenant.mappers.product;

import com.interfija.masterposmultitenant.dto.product.ProductBranchDTO;
import com.interfija.masterposmultitenant.dto.product.ProductBranchPriceDTO;
import com.interfija.masterposmultitenant.dto.supplier.SupplierDTO;
import com.interfija.masterposmultitenant.entities.tenant.product.ProductBranchEntity;
import com.interfija.masterposmultitenant.entities.tenant.product.ProductBranchPriceEntity;
import com.interfija.masterposmultitenant.entities.tenant.supplier.SupplierEntity;
import com.interfija.masterposmultitenant.mappers.base.BaseMapper;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import org.springframework.stereotype.Service;

@Service
public class ProductBranchPriceMapper extends BaseMapper implements GenericMapper<ProductBranchPriceEntity, ProductBranchPriceDTO> {

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductBranchPriceDTO toDto(ProductBranchPriceEntity entity) {
        if (entity == null) {
            return null;
        }

        SupplierDTO supplierDTO;
        if (entity.getSupplierEntity() != null) {
            supplierDTO = SupplierDTO.builder()
                    .idSupplier(entity.getSupplierEntity().getIdSupplier())
                    .names(entity.getSupplierEntity().getNames())
                    .lastNames(entity.getSupplierEntity().getLastNames())
                    .build();
        } else {
            supplierDTO = null;
        }

        ProductBranchDTO productBranchDTO;
        if (entity.getProductBranchEntity() != null) {
            productBranchDTO = ProductBranchDTO.builder()
                    .idProductBranch(entity.getProductBranchEntity().getIdProductBranch())
                    .build();
        } else {
            productBranchDTO = null;
        }

        return ProductBranchPriceDTO.builder()
                .idProductBranchPrice(entity.getIdProductBranchPrice())
                .purchasePrice(entity.getPurchasePrice())
                .salePrice(entity.getSalePrice())
                .defaultPrice(entity.getDefaultPrice())
                .productBranchDTO(productBranchDTO)
                .supplierDTO(supplierDTO)
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductBranchPriceEntity toEntity(ProductBranchPriceDTO dto) {
        if (dto == null) {
            return null;
        }

        SupplierEntity supplierEntity;
        if (dto.getSupplierDTO() != null) {
            supplierEntity = SupplierEntity.builder()
                    .idSupplier(dto.getSupplierDTO().getIdSupplier())
                    .build();
        } else {
            supplierEntity = null;
        }

        ProductBranchEntity productBranchEntity;
        if (dto.getProductBranchDTO() != null) {
            productBranchEntity = ProductBranchEntity.builder()
                    .idProductBranch(dto.getProductBranchDTO().getIdProductBranch())
                    .build();
        } else {
            productBranchEntity = null;
        }

        return ProductBranchPriceEntity.builder()
                .idProductBranchPrice(dto.getIdProductBranchPrice())
                .purchasePrice(defaultBigDecimal(dto.getPurchasePrice()))
                .salePrice(dto.getSalePrice())
                .defaultPrice(dto.isDefaultPrice())
                .productBranchEntity(productBranchEntity)
                .supplierEntity(supplierEntity)
                .build();
    }

    public void updateExistingEntity(ProductBranchPriceEntity entity, ProductBranchPriceDTO dto) {
        SupplierEntity supplierEntity;
        if (dto.getSupplierDTO() != null) {
            supplierEntity = SupplierEntity.builder()
                    .idSupplier(dto.getSupplierDTO().getIdSupplier())
                    .build();
        } else {
            supplierEntity = null;
        }

        entity.setPurchasePrice(defaultBigDecimal(dto.getPurchasePrice()));
        entity.setSalePrice(dto.getSalePrice());
        entity.setSupplierEntity(supplierEntity);
        entity.setDefaultPrice(dto.isDefaultPrice());
    }

}
