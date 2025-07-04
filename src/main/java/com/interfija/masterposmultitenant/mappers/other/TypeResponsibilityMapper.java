package com.interfija.masterposmultitenant.mappers.other;

import com.interfija.masterposmultitenant.dto.other.TypeResponsibilityDTO;
import com.interfija.masterposmultitenant.entities.tenant.other.TypeResponsibilityEntity;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import org.springframework.stereotype.Component;

@Component
public class TypeResponsibilityMapper implements GenericMapper<TypeResponsibilityEntity, TypeResponsibilityDTO> {

    /**
     * {@inheritDoc}
     */
    @Override
    public TypeResponsibilityDTO toDto(TypeResponsibilityEntity entity) {
        if (entity == null) {
            return null;
        }

        return TypeResponsibilityDTO.builder()
                .idTypeResponsibility(entity.getIdTypeResponsibility())
                .name(entity.getName())
                .code(entity.getCode())
                .apiResponsibilityId(entity.getApiResponsibilityId())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypeResponsibilityEntity toEntity(TypeResponsibilityDTO dto) {
        if (dto == null) {
            return null;
        }

        return TypeResponsibilityEntity.builder()
                .idTypeResponsibility(dto.getIdTypeResponsibility())
                .name(dto.getName())
                .code(dto.getCode())
                .apiResponsibilityId(dto.getApiResponsibilityId())
                .build();
    }

}
