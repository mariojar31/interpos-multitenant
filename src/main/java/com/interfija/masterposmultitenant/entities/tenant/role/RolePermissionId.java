package com.interfija.masterposmultitenant.entities.tenant.role;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class RolePermissionId implements Serializable {

    /**
     * Identificador de serialización para asegurar compatibilidad durante el proceso de serialización y deserialización.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    private Short roleId;
    private Short permissionId;
    private Long branchId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RolePermissionId that)) return false;
        return Objects.equals(roleId, that.roleId)
               && Objects.equals(permissionId, that.permissionId)
               && Objects.equals(branchId, that.branchId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, permissionId, branchId);
    }

}
