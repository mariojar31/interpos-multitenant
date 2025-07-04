package com.interfija.masterposmultitenant.mappers.role;

import com.interfija.masterposmultitenant.dto.role.PermissionDTO;
import com.interfija.masterposmultitenant.entities.tenant.role.PermissionEntity;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import org.springframework.stereotype.Component;

@Component
public class PermissionMapper implements GenericMapper<PermissionEntity, PermissionDTO> {

    private final PermissionMapper permissionMapper = this;

    /**
     * {@inheritDoc}
     */
    @Override
    public PermissionDTO toDto(PermissionEntity entity) {
        if (entity == null) {
            return null;
        }

        return PermissionDTO.builder()
                .idPermission(entity.getIdPermission())
                .name(entity.getName())
                .parentDTO(permissionMapper.toDto(entity.getParent()))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PermissionEntity toEntity(PermissionDTO dto) {
        if (dto == null) {
            return null;
        }

        return PermissionEntity.builder()
                .idPermission(dto.getIdPermission())
                .name(dto.getName())
                .parent(permissionMapper.toEntity(dto.getParentDTO()))
                .children(toEntityList(dto.getChildrenList()))
                .build();
    }

}
