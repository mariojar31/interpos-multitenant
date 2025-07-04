package com.interfija.masterposmultitenant.mappers.other;

import com.interfija.masterposmultitenant.dto.other.TaxDTO;
import com.interfija.masterposmultitenant.entities.tenant.other.TaxEntity;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import org.springframework.stereotype.Component;

@Component
public class TaxMapper implements GenericMapper<TaxEntity, TaxDTO> {

    /**
     * {@inheritDoc}
     */
    @Override
    public TaxDTO toDto(TaxEntity entity) {
        if (entity == null) {
            return null;
        }

        return TaxDTO.builder()
                .idTax(entity.getIdTax())
                .name(entity.getName())
                .code(entity.getCode())
                .apiTaxId(entity.getApiTaxId())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TaxEntity toEntity(TaxDTO dto) {
        if (dto == null) {
            return null;
        }

        return TaxEntity.builder()
                .idTax(dto.getIdTax())
                .name(dto.getName())
                .code(dto.getCode())
                .apiTaxId(dto.getApiTaxId())
                .build();
    }

}
