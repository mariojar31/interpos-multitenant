package com.interfija.masterposmultitenant.repository.other;

import com.interfija.masterposmultitenant.entities.tenant.other.BarcodeTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarcodeTypeRepository extends JpaRepository<BarcodeTypeEntity, Short> {
}
