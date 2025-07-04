package com.interfija.masterposmultitenant.repository.role;

import com.interfija.masterposmultitenant.entities.tenant.role.PermissionEntity;
import com.interfija.masterposmultitenant.entities.tenant.role.RolePermissionEntity;
import com.interfija.masterposmultitenant.entities.tenant.role.RolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermissionEntity, RolePermissionId> {

    @Query("SELECT rp.permissionEntity FROM RolePermissionEntity rp WHERE rp.roleEntity.idRole = :roleId")
    List<PermissionEntity> findAllPermissionsByRoleId(@Param("roleId") Long roleId);

    @Query("SELECT rp.permissionEntity FROM RolePermissionEntity rp WHERE rp.branchEntity.idBranch = :branchId AND rp.roleEntity.idRole = :roleId")
    List<PermissionEntity> findAllPermissionsByBranchIdAndRoleId(@Param("branchId") Long branchId, @Param("roleId") Long roleId);
}
