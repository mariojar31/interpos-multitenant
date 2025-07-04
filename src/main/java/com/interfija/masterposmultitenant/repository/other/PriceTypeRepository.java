package com.interfija.masterposmultitenant.repository.other;

import com.interfija.masterposmultitenant.entities.tenant.other.PriceTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceTypeRepository extends JpaRepository<PriceTypeEntity, Short> {}
