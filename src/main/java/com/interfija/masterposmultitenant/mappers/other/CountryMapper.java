package com.interfija.masterposmultitenant.mappers.other;

import com.interfija.masterposmultitenant.dto.other.CountryDTO;
import com.interfija.masterposmultitenant.entities.tenant.other.CountryEntity;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper implements GenericMapper<CountryEntity, CountryDTO> {

    /**
     * {@inheritDoc}
     */
    @Override
    public CountryDTO toDto(CountryEntity entity) {
        if (entity == null) {
            return null;
        }

        return CountryDTO.builder()
                .idCountry(entity.getIdCountry())
                .name(entity.getName())
                .code(entity.getCode())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CountryEntity toEntity(CountryDTO dto) {
        if (dto == null) {
            return null;
        }

        return CountryEntity.builder()
                .idCountry(dto.getIdCountry())
                .name(dto.getName())
                .code(dto.getCode())
                .build();
    }

}
