package com.interfija.masterposmultitenant.repository;

import com.interfija.masterposmultitenant.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    // Si necesitas buscar por username y tenant
    Optional<User> findByUsernameAndTenantId(String username, String tenantId);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}