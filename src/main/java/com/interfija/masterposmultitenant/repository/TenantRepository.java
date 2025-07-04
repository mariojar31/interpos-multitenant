package com.interfija.masterposmultitenant.repository;

import com.interfija.masterposmultitenant.entities.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TenantRepository extends JpaRepository<Tenant, String> {
    boolean existsByName(String name);
    boolean existsByDbName(String dbName);
    Optional<Tenant> findByDbName(String dbName);

    boolean existsByIdAndActiveTrue(String id);
    Optional<Tenant> findByIdAndActiveTrue(String id);
}