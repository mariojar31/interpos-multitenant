package com.interfija.masterposmultitenant.mappers.other;

import com.interfija.masterposmultitenant.dto.other.TypeUnitDTO;
import com.interfija.masterposmultitenant.entities.tenant.other.TypeUnitEntity;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import org.springframework.stereotype.Component;

@Component
public class TypeUnitMapper implements GenericMapper<TypeUnitEntity, TypeUnitDTO> {

    /**
     * {@inheritDoc}
     */
    @Override
    public TypeUnitDTO toDto(TypeUnitEntity entity) {
        if (entity == null) {
            return null;
        }

        return TypeUnitDTO.builder()
                .idTypeUnit(entity.getIdTypeUnit())
                .name(entity.getName())
                .abbreviation(entity.getAbbreviation())
                .baseValue(entity.getBaseValue())
                .apiUnitId(entity.getApiUnitId())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypeUnitEntity toEntity(TypeUnitDTO dto) {
        if (dto == null) {
            return null;
        }

        return TypeUnitEntity.builder()
                .idTypeUnit(dto.getIdTypeUnit())
                .name(dto.getName())
                .abbreviation(dto.getAbbreviation())
                .baseValue(dto.getBaseValue())
                .apiUnitId(dto.getApiUnitId())
                .build();
    }

}
