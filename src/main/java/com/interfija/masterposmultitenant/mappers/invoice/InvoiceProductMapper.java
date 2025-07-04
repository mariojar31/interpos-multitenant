package com.interfija.masterposmultitenant.mappers.invoice;

import com.interfija.masterposmultitenant.dto.category.CategoryDTO;
import com.interfija.masterposmultitenant.dto.invoice.InvoiceProductDTO;
import com.interfija.masterposmultitenant.dto.product.ProductBranchDTO;
import com.interfija.masterposmultitenant.entities.tenant.invoice.InvoiceEntity;
import com.interfija.masterposmultitenant.entities.tenant.invoice.InvoiceProductEntity;
import com.interfija.masterposmultitenant.mappers.base.BaseMapper;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import com.interfija.masterposmultitenant.mappers.other.TypeUnitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class InvoiceProductMapper extends BaseMapper implements GenericMapper<InvoiceProductEntity, InvoiceProductDTO> {

    private final TypeUnitMapper typeUnitMapper;

    @Autowired
    public InvoiceProductMapper(TypeUnitMapper typeUnitMapper) {
        this.typeUnitMapper = typeUnitMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InvoiceProductDTO toDto(InvoiceProductEntity entity) {
        if (entity == null) {
            return null;
        }

        CategoryDTO categoryDTO = CategoryDTO.builder()
                .name(entity.getCategoryName())
                .build();

        return InvoiceProductDTO.builder()
                .idInvoiceProduct(entity.getIdInvoiceProduct())
                .reference(entity.getReference())
                .barcode(entity.getBarcode())
                .typeUnitDTO(typeUnitMapper.toDto(entity.getTypeUnitEntity()))
                .name(entity.getName())
                .quantity(entity.getQuantity())
                .oldQuantity(entity.getQuantity())
                .purchasePrice(entity.getPurchasePrice())
                .salePrice(entity.getSalePrice())
                .discountType(entity.getDiscountType())
                .valueDiscount(entity.getValueDiscount())
                .commissionType(entity.getCommissionType())
                .valueCommission(entity.getValueCommission())
                .categoryDTO(categoryDTO)
                .taxesList(new ArrayList<>())
                .service(entity.getService())
                .variablePrice(entity.getVariablePrice())
                .bundle(entity.getBundle())
                .observation(entity.getObservation())
                .productBranchDTO(new ProductBranchDTO(entity.getProductStockId()))
                .invoiceId(entity.getInvoiceEntity().getIdInvoice())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InvoiceProductEntity toEntity(InvoiceProductDTO dto) {
        if (dto == null) {
            return null;
        }

        return InvoiceProductEntity.builder()
                .idInvoiceProduct(dto.getIdInvoiceProduct())
                .reference(dto.getReference())
                .barcode(dto.getBarcode())
                .typeUnitEntity(typeUnitMapper.toEntity(dto.getTypeUnitDTO()))
                .name(dto.getName())
                .quantity(dto.getQuantity())
                .purchasePrice(dto.getPurchasePrice())
                .salePrice(dto.getSalePrice())
                .discountType(defaultNullString(dto.getDiscountType()))
                .valueDiscount(defaultBigDecimal(dto.getValueDiscount()))
                .commissionType(defaultNullString(dto.getCommissionType()))
                .valueCommission(defaultBigDecimal(dto.getValueCommission()))
                .totalDiscount(dto.getTotalDiscount())
                .totalTax(dto.getTotalTaxes())
                .subtotal(dto.getSubtotal())
                .totalRetention(dto.getTotalRetentions())
                .totalCommission(dto.getTotalCommission())
                .total(dto.getTotal())
                .finalPrice(dto.getFinalPrice())
                .categoryName(dto.getCategoryDTO().getName())
                .employees(new ArrayList<>())
                .taxes(new ArrayList<>())
                .service(dto.isService())
                .variablePrice(dto.isVariablePrice())
                .bundle(dto.isBundle())
                .observation(defaultNullString(dto.getObservation()))
                .productStockId(dto.getProductBranchDTO().getIdProductBranch())
                .invoiceEntity(new InvoiceEntity(dto.getInvoiceId()))
                .build();
    }

    public void updateExistingEntity(InvoiceProductEntity entity, InvoiceProductDTO dto) {
        entity.setName(dto.getName());
        entity.setQuantity(dto.getQuantity());
        entity.setSalePrice(dto.getSalePrice());
        entity.setDiscountType(defaultNullString(dto.getDiscountType()));
        entity.setValueDiscount(defaultBigDecimal(dto.getValueDiscount()));
        entity.setObservation(defaultNullString(dto.getObservation()));
        entity.setTotalDiscount(dto.getTotalDiscount());
        entity.setTotalTax(dto.getTotalTaxes());
        entity.setSubtotal(dto.getSubtotal());
        entity.setTotalRetention(dto.getTotalRetentions());
        entity.setTotalCommission(dto.getTotalCommission());
        entity.setFinalPrice(dto.getFinalPrice());
        entity.setTotal(dto.getTotal());
    }

    public void updateExistingEntity(InvoiceProductEntity entity, InvoiceProductEntity entity1) {
        entity.setName(entity1.getName());
        entity.setQuantity(entity1.getQuantity());
        entity.setSalePrice(entity1.getSalePrice());
        entity.setDiscountType(defaultNullString(entity1.getDiscountType()));
        entity.setValueDiscount(defaultBigDecimal(entity1.getValueDiscount()));
        entity.setObservation(defaultNullString(entity1.getObservation()));
        entity.setTotalDiscount(entity1.getTotalDiscount());
        entity.setTotalTax(entity1.getTotalTax());
        entity.setSubtotal(entity1.getSubtotal());
        entity.setTotalRetention(entity1.getTotalRetention());
        entity.setTotalCommission(entity1.getTotalCommission());
        entity.setFinalPrice(entity1.getFinalPrice());
        entity.setTotal(entity1.getTotal());
    }

}
