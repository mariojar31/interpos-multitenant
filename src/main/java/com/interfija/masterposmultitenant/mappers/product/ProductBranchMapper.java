package com.interfija.masterposmultitenant.mappers.product;

import com.interfija.masterposmultitenant.dto.branch.BranchDTO;
import com.interfija.masterposmultitenant.dto.product.ProductBranchDTO;
import com.interfija.masterposmultitenant.dto.product.ProductDTO;
import com.interfija.masterposmultitenant.entities.tenant.branch.BranchEntity;
import com.interfija.masterposmultitenant.entities.tenant.product.ProductEntity;
import com.interfija.masterposmultitenant.entities.tenant.product.ProductBranchEntity;
import com.interfija.masterposmultitenant.mappers.base.BaseMapper;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductBranchMapper extends BaseMapper implements GenericMapper<ProductBranchEntity, ProductBranchDTO> {

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductBranchDTO toDto(ProductBranchEntity entity) {
        if (entity == null) {
            return null;
        }

        ProductDTO productDTO = ProductDTO.builder()
                .idProduct(entity.getProductEntity().getIdProduct())
                .name(entity.getProductEntity().getName())
                .build();

        BranchDTO branchDTO = BranchDTO.builder()
                .idBranch(entity.getBranchEntity().getIdBranch())
                .name(entity.getBranchEntity().getName())
                .build();

        return ProductBranchDTO.builder()
                .idProductBranch(entity.getIdProductBranch())
                .commissionType(entity.getCommissionType())
                .valueCommission(entity.getValueCommission())
                .printerNumber(Byte.toString(entity.getPrinterNumber()))
                .quantity(entity.getQuantity())
                .visible(entity.getVisible())
                .pricesList(new ArrayList<>())
                .batchesList(new ArrayList<>())
                .productDTO(productDTO)
                .branchDTO(branchDTO)
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductBranchEntity toEntity(ProductBranchDTO dto) {
        if (dto == null) {
            return null;
        }

        ProductEntity productEntity = ProductEntity.builder()
                .idProduct(dto.getProductDTO().getIdProduct())
                .build();

        BranchEntity branchEntity = BranchEntity.builder()
                .idBranch(dto.getBranchDTO().getIdBranch())
                .build();

        return ProductBranchEntity.builder()
                .idProductBranch(dto.getIdProductBranch())
                .commissionType(defaultNullString(dto.getCommissionType()))
                .valueCommission(defaultBigDecimal(dto.getValueCommission()))
                .printerNumber(dto.getPrinterNumber() == null ? 1 : Byte.parseByte(dto.getPrinterNumber()))
                .quantity(defaultBigDecimal(dto.getQuantity()))
                .visible(dto.isVisible())
                .prices(new ArrayList<>())
                .batches(new ArrayList<>())
                .productEntity(productEntity)
                .branchEntity(branchEntity)
                .build();
    }

    public void updateExistingEntity(ProductBranchEntity entity, ProductBranchDTO dto) {
        entity.setQuantity(defaultBigDecimal(dto.getQuantity()));
        entity.setCommissionType(defaultNullString(dto.getCommissionType()));
        entity.setValueCommission(defaultBigDecimal(dto.getValueCommission()));
        entity.setPrinterNumber(Byte.parseByte(dto.getPrinterNumber()));
        entity.setVisible(dto.isVisible());
    }

    public void updateExistingEntity(ProductBranchEntity entity, ProductBranchEntity entity1) {
        entity.setQuantity(defaultBigDecimal(entity1.getQuantity()));
        entity.setCommissionType(defaultNullString(entity1.getCommissionType()));
        entity.setValueCommission(defaultBigDecimal(entity1.getValueCommission()));
        entity.setPrinterNumber(entity1.getPrinterNumber());
        entity.setVisible(entity1.getVisible());
    }

}
