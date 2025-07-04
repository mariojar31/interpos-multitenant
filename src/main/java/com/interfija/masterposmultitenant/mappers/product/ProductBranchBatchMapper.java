package com.interfija.masterposmultitenant.mappers.product;

import com.interfija.masterposmultitenant.dto.product.ProductBranchBatchDTO;
import com.interfija.masterposmultitenant.dto.product.ProductBranchDTO;
import com.interfija.masterposmultitenant.entities.tenant.product.ProductBranchEntity;
import com.interfija.masterposmultitenant.entities.tenant.product.ProductBranchBatchEntity;
import com.interfija.masterposmultitenant.mappers.base.BaseMapper;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductBranchBatchMapper extends BaseMapper implements GenericMapper<ProductBranchBatchEntity, ProductBranchBatchDTO> {

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductBranchBatchDTO toDto(ProductBranchBatchEntity entity) {
        if (entity == null) {
            return null;
        }

        ProductBranchDTO productBranchDTO;
        if (entity.getProductBranchEntity() != null) {
            productBranchDTO = ProductBranchDTO.builder()
                    .idProductBranch(entity.getProductBranchEntity().getIdProductBranch())
                    .build();
        } else {
            productBranchDTO = null;
        }

        return ProductBranchBatchDTO.builder()
                .idProductBranchBatch(entity.getIdProductBranchBatch())
                .batchCode(entity.getBatchCode())
                .serialNumber(entity.getSerialNumber())
                .quantity(entity.getQuantity())
                .entryDate(entity.getEntryDate())
                .expeditionDate(entity.getExpeditionDate())
                .expirationDate(entity.getExpirationDate())
                .productBranchDTO(productBranchDTO)
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductBranchBatchEntity toEntity(ProductBranchBatchDTO dto) {
        if (dto == null) {
            return null;
        }

        ProductBranchEntity productBranchEntity;
        if (dto.getProductBranchDTO() != null) {
            productBranchEntity = ProductBranchEntity.builder()
                    .idProductBranch(dto.getProductBranchDTO().getIdProductBranch())
                    .build();
        } else {
            productBranchEntity = null;
        }

        return ProductBranchBatchEntity.builder()
                .idProductBranchBatch(dto.getIdProductBranchBatch())
                .batchCode(dto.getBatchCode())
                .serialNumber(dto.getSerialNumber())
                .quantity(dto.getQuantity())
                .entryDate(dto.getEntryDate())
                .expeditionDate(dto.getExpeditionDate())
                .expirationDate(dto.getExpirationDate())
                .productBranchEntity(productBranchEntity)
                .build();
    }

    public void updateExistingEntity(ProductBranchBatchEntity entity, ProductBranchBatchDTO dto) {
        entity.setBatchCode(dto.getBatchCode());
        entity.setSerialNumber(dto.getSerialNumber());
        entity.setQuantity(dto.getQuantity());
        entity.setEntryDate(dto.getEntryDate());
        entity.setExpeditionDate(dto.getExpeditionDate());
        entity.setExpirationDate(dto.getExpirationDate());
    }

}
