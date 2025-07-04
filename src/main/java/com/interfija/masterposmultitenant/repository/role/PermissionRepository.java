package com.interfija.masterposmultitenant.repository.role;

import com.interfija.masterposmultitenant.entities.tenant.role.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {
}
