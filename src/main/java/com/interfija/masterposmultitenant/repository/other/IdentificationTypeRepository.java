package com.interfija.masterposmultitenant.repository.other;

import com.interfija.masterposmultitenant.entities.tenant.other.IdentificationTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdentificationTypeRepository extends JpaRepository<IdentificationTypeEntity, Short> {}
