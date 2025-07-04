package com.interfija.masterposmultitenant.repository.role;

import com.interfija.masterposmultitenant.entities.tenant.role.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Short> {

    @Query("SELECT r FROM RoleEntity r WHERE r.idRole NOT IN :excludedIds")
    List<RoleEntity> findAllExcludingIds(@Param("excludedIds") List<Short> excludedIds);

}
