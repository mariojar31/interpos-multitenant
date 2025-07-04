package com.interfija.masterposmultitenant.mappers.other;

import com.interfija.masterposmultitenant.dto.other.TypeRegimeDTO;
import com.interfija.masterposmultitenant.entities.tenant.other.TypeRegimeEntity;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import org.springframework.stereotype.Component;

@Component
public class TypeRegimeMapper implements GenericMapper<TypeRegimeEntity, TypeRegimeDTO> {

    /**
     * {@inheritDoc}
     */
    @Override
    public TypeRegimeDTO toDto(TypeRegimeEntity entity) {
        if (entity == null) {
            return null;
        }

        return TypeRegimeDTO.builder()
                .idTypeRegime(entity.getIdTypeRegime())
                .name(entity.getName())
                .code(entity.getCode())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypeRegimeEntity toEntity(TypeRegimeDTO dto) {
        if (dto == null) {
            return null;
        }

        return TypeRegimeEntity.builder()
                .idTypeRegime(dto.getIdTypeRegime())
                .name(dto.getName())
                .code(dto.getCode())
                .build();
    }

}
