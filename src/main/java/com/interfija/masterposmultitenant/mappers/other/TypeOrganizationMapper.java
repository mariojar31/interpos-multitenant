package com.interfija.masterposmultitenant.mappers.other;

import com.interfija.masterposmultitenant.dto.other.TypeOrganizationDTO;
import com.interfija.masterposmultitenant.entities.tenant.other.TypeOrganizationEntity;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import org.springframework.stereotype.Component;

@Component
public class TypeOrganizationMapper implements GenericMapper<TypeOrganizationEntity, TypeOrganizationDTO> {

    /**
     * {@inheritDoc}
     */
    @Override
    public TypeOrganizationDTO toDto(TypeOrganizationEntity entity) {
        if (entity == null) {
            return null;
        }

        return TypeOrganizationDTO.builder()
                .idTypeOrganization(entity.getIdTypeOrganization())
                .name(entity.getName())
                .code(entity.getCode())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypeOrganizationEntity toEntity(TypeOrganizationDTO dto) {
        if (dto == null) {
            return null;
        }

        return TypeOrganizationEntity.builder()
                .idTypeOrganization(dto.getIdTypeOrganization())
                .name(dto.getName())
                .code(dto.getCode())
                .build();
    }

}
