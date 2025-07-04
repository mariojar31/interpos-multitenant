package com.interfija.masterposmultitenant.entities.tenant.role;

import com.interfija.masterposmultitenant.entities.tenant.branch.BranchEntity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role_permission")
public class RolePermissionEntity {

    /**
     * Clave primaria compuesta de la entidad, formada por los identificadores de:
     * - Role
     * - Permiso
     * - Sucursal
     */
    @EmbeddedId
    private RolePermissionId idRolePermission;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity roleEntity;

    @ManyToOne
    @MapsId("permissionId")
    @JoinColumn(name = "permission_id", nullable = false)
    private PermissionEntity permissionEntity;

    @ManyToOne
    @MapsId("branchId")
    @JoinColumn(name = "branch_id", nullable = false)
    private BranchEntity branchEntity;

}
