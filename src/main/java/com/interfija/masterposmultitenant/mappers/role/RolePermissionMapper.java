package com.interfija.masterposmultitenant.mappers.role;

import com.interfija.masterposmultitenant.dto.role.RolePermissionDTO;
import com.interfija.masterposmultitenant.entities.tenant.branch.BranchEntity;
import com.interfija.masterposmultitenant.entities.tenant.role.PermissionEntity;
import com.interfija.masterposmultitenant.entities.tenant.role.RoleEntity;
import com.interfija.masterposmultitenant.entities.tenant.role.RolePermissionEntity;
import com.interfija.masterposmultitenant.entities.tenant.role.RolePermissionId;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import org.springframework.stereotype.Component;

@Component
public class RolePermissionMapper implements GenericMapper<RolePermissionEntity, RolePermissionDTO> {

    /**
     * {@inheritDoc}
     */
    @Override
    public RolePermissionDTO toDto(RolePermissionEntity entity) {
        if (entity == null) {
            return null;
        }

        return RolePermissionDTO.builder()
                .permissionId(entity.getPermissionEntity().getIdPermission())
                .roleId(entity.getRoleEntity().getIdRole())
                .branchId(entity.getBranchEntity().getIdBranch())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RolePermissionEntity toEntity(RolePermissionDTO dto) {
        if (dto == null) {
            return null;
        }

        Short roleId = dto.getRoleId();
        Short permissionId = dto.getPermissionId();
        Long branchId = dto.getBranchId();

        BranchEntity branchEntity = BranchEntity.builder()
                .idBranch(branchId)
                .build();

        return RolePermissionEntity.builder()
                .idRolePermission(new RolePermissionId(roleId, permissionId, branchId))
                .roleEntity(new RoleEntity(roleId))
                .permissionEntity(new PermissionEntity(permissionId))
                .branchEntity(branchEntity)
                .build();
    }

}
