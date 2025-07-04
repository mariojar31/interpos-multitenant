package com.interfija.masterposmultitenant.mappers.role;

import com.interfija.masterposmultitenant.dto.role.RoleDTO;
import com.interfija.masterposmultitenant.entities.tenant.role.RoleEntity;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper implements GenericMapper<RoleEntity, RoleDTO> {

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleDTO toDto(RoleEntity entity) {
        if (entity == null) {
            return null;
        }

        return RoleDTO.builder()
                .idRole(entity.getIdRole())
                .name(entity.getName())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleEntity toEntity(RoleDTO dto) {
        if (dto == null) {
            return null;
        }

        return RoleEntity.builder()
                .idRole(dto.getIdRole())
                .name(dto.getName())
                .build();
    }

}
