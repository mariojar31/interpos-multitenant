package com.interfija.masterposmultitenant.mappers.invoice;

import com.interfija.masterposmultitenant.dto.invoice.InvoiceTaxDTO;
import com.interfija.masterposmultitenant.entities.tenant.invoice.InvoiceEntity;
import com.interfija.masterposmultitenant.entities.tenant.invoice.InvoiceTaxEntity;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import com.interfija.masterposmultitenant.mappers.other.TypeTaxMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvoiceTaxMapper implements GenericMapper<InvoiceTaxEntity, InvoiceTaxDTO> {

    private final TypeTaxMapper typeTaxMapper;

    @Autowired
    public InvoiceTaxMapper(TypeTaxMapper typeTaxMapper) {
        this.typeTaxMapper = typeTaxMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InvoiceTaxDTO toDto(InvoiceTaxEntity entity) {
        if (entity == null) {
            return null;
        }

        return InvoiceTaxDTO.builder()
                .idInvoiceTax(entity.getIdInvoiceTax())
                .typeTaxDTO(typeTaxMapper.toDto(entity.getTypeTaxEntity()))
                .baseAmount(entity.getBaseAmount())
                .totalTax(entity.getTotalTax())
                .invoiceId(entity.getInvoiceEntity().getIdInvoice())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InvoiceTaxEntity toEntity(InvoiceTaxDTO dto) {
        if (dto == null) {
            return null;
        }

        return InvoiceTaxEntity.builder()
                .idInvoiceTax(dto.getIdInvoiceTax())
                .typeTaxEntity(typeTaxMapper.toEntity(dto.getTypeTaxDTO()))
                .baseAmount(dto.getBaseAmount())
                .totalTax(dto.getTotalTax())
                .invoiceEntity(new InvoiceEntity(dto.getInvoiceId()))
                .build();
    }

    public void updateExistingEntity(InvoiceTaxEntity entity, InvoiceTaxDTO dto) {

    }

}
