package com.interfija.masterposmultitenant.repository.other;

import com.interfija.masterposmultitenant.entities.tenant.other.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Short> {}
