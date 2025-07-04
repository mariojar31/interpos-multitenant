package com.interfija.masterposmultitenant.repository.other;

import com.interfija.masterposmultitenant.entities.tenant.other.MunicipalityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MunicipalityRepository extends JpaRepository<MunicipalityEntity, Integer> {

    @Query("SELECT m FROM MunicipalityEntity m JOIN FETCH m.departmentEntity WHERE m.departmentEntity.idDepartment = :departmentId")
    List<MunicipalityEntity> findByDepartmentId(@Param("departmentId") Short departmentId);

}
