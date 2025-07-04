package com.interfija.masterposmultitenant.mappers.other;

import com.interfija.masterposmultitenant.dto.other.TypeTaxDTO;
import com.interfija.masterposmultitenant.entities.tenant.other.TypeTaxEntity;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TypeTaxMapper implements GenericMapper<TypeTaxEntity, TypeTaxDTO> {

    private final TaxMapper taxMapper;

    @Autowired
    public TypeTaxMapper(TaxMapper taxMapper) {
        this.taxMapper = taxMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypeTaxDTO toDto(TypeTaxEntity entity) {
        if (entity == null) {
            return null;
        }

        return TypeTaxDTO.builder()
                .idTypeTax(entity.getIdTypeTax())
                .name(entity.getName())
                .rate(entity.getRate())
                .taxDTO(taxMapper.toDto(entity.getTaxEntity()))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypeTaxEntity toEntity(TypeTaxDTO dto) {
        if (dto == null) {
            return null;
        }

        return TypeTaxEntity.builder()
                .idTypeTax(dto.getIdTypeTax())
                .name(dto.getName())
                .rate(dto.getRate())
                .taxEntity(taxMapper.toEntity(dto.getTaxDTO()))
                .build();
    }

}
