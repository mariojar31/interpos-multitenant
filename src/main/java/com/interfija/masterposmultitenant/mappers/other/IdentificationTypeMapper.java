package com.interfija.masterposmultitenant.mappers.other;

import com.interfija.masterposmultitenant.dto.other.IdentificationTypeDTO;
import com.interfija.masterposmultitenant.entities.tenant.other.IdentificationTypeEntity;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import org.springframework.stereotype.Component;

@Component
public class IdentificationTypeMapper implements GenericMapper<IdentificationTypeEntity, IdentificationTypeDTO> {

    /**
     * {@inheritDoc}
     */
    @Override
    public IdentificationTypeDTO toDto(IdentificationTypeEntity entity) {
        if (entity == null) {
            return null;
        }

        return IdentificationTypeDTO.builder()
                .idIdentificationType(entity.getIdIdentificationType())
                .name(entity.getName())
                .code(entity.getCode())
                .abbreviation(entity.getAbbreviation())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IdentificationTypeEntity toEntity(IdentificationTypeDTO dto) {
        if (dto == null) {
            return null;
        }

        return IdentificationTypeEntity.builder()
                .idIdentificationType(dto.getIdIdentificationType())
                .name(dto.getName())
                .code(dto.getCode())
                .abbreviation(dto.getAbbreviation())
                .build();
    }

}
