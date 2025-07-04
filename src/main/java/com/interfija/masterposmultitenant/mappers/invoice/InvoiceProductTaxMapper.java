package com.interfija.masterposmultitenant.mappers.invoice;

import com.interfija.masterposmultitenant.dto.invoice.InvoiceProductTaxDTO;
import com.interfija.masterposmultitenant.entities.tenant.invoice.InvoiceProductEntity;
import com.interfija.masterposmultitenant.entities.tenant.invoice.InvoiceProductTaxEntity;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import com.interfija.masterposmultitenant.mappers.other.TypeTaxMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvoiceProductTaxMapper implements GenericMapper<InvoiceProductTaxEntity, InvoiceProductTaxDTO> {

    private final TypeTaxMapper typeTaxMapper;

    @Autowired
    public InvoiceProductTaxMapper(TypeTaxMapper typeTaxMapper) {
        this.typeTaxMapper = typeTaxMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InvoiceProductTaxDTO toDto(InvoiceProductTaxEntity entity) {
        if (entity == null) {
            return null;
        }

        return InvoiceProductTaxDTO.builder()
                .idInvoiceProductTax(entity.getIdInvoiceProductTax())
                .typeTaxDTO(typeTaxMapper.toDto(entity.getTypeTaxEntity()))
                .totalTax(entity.getTotalTax())
                .invoiceProductId(entity.getInvoiceProductEntity().getIdInvoiceProduct())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InvoiceProductTaxEntity toEntity(InvoiceProductTaxDTO dto) {
        if (dto == null) {
            return null;
        }

        return InvoiceProductTaxEntity.builder()
                .idInvoiceProductTax(dto.getIdInvoiceProductTax())
                .typeTaxEntity(typeTaxMapper.toEntity(dto.getTypeTaxDTO()))
                .totalTax(dto.getTotalTax())
                .invoiceProductEntity(new InvoiceProductEntity(dto.getInvoiceProductId()))
                .build();
    }

    public void updateExistingEntity(InvoiceProductTaxEntity entity, InvoiceProductTaxDTO dto) {

    }

}
