package com.interfija.masterposmultitenant.repository.employee;

import com.interfija.masterposmultitenant.entities.tenant.employee.EmployeeEntity;
import com.interfija.masterposmultitenant.repository.employee.projections.EmployeeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    @Query("""
                SELECT
                    e.idEmployee AS idEmployee,
                    e.names AS names,
                    e.lastNames AS lastNames,
                    e.branchEntity.idBranch AS branchId,
                    e.branchEntity.name AS branchName
                FROM EmployeeEntity e
                WHERE e.visible = :visible AND e.idEmployee != 1
            """)
    List<EmployeeProjection> findAllProjectedBasicByVisible(@Param("visible") Boolean visible);

    @Query("""
                SELECT
                    e.idEmployee AS idEmployee,
                    e.names AS names,
                    e.lastNames AS lastNames,
                    e.phone AS phone,
                    e.identificationNumber AS identificationNumber,
                    e.branchEntity.idBranch AS branchId,
                    e.branchEntity.name AS branchName,
                    e.branchEntity.companyEntity.idCompany AS companyId,
                    e.branchEntity.companyEntity.name AS companyName,
                    e.roleEntity.idRole AS roleId,
                    e.roleEntity.name AS roleName
                FROM EmployeeEntity e
                WHERE e.visible = :visible AND e.idEmployee != 1
            """)
    List<EmployeeProjection> findAllProjectedSummaryByVisible(@Param("visible") Boolean visible);

    @Query("""
              SELECT
                    e.idEmployee AS idEmployee,
                    e.names AS names,
                    e.lastNames AS lastNames,
                    e.branchEntity.idBranch AS branchId,
                    e.branchEntity.name AS branchName
                FROM EmployeeEntity e
                WHERE e.branchEntity.companyEntity.idCompany = :companyId AND e.visible = :visible AND e.idEmployee != 1
            """)
    List<EmployeeProjection> findAllProjectedBasicByCompanyIdAndVisible(@Param("companyId") Long companyId, @Param("visible") Boolean visible);

    @Query("""
                SELECT
                    e.idEmployee AS idEmployee,
                    e.names AS names,
                    e.lastNames AS lastNames,
                    e.phone AS phone,
                    e.identificationNumber AS identificationNumber,
                    e.branchEntity.idBranch AS branchId,
                    e.branchEntity.name AS branchName,
                    e.branchEntity.companyEntity.idCompany AS companyId,
                    e.branchEntity.companyEntity.name AS companyName,
                    e.roleEntity.idRole AS roleId,
                    e.roleEntity.name AS roleName
                FROM EmployeeEntity e
                WHERE e.branchEntity.companyEntity.idCompany = :companyId AND e.visible = :visible AND e.idEmployee != 1
            """)
    List<EmployeeProjection> findAllProjectedSummaryByCompanyIdAndVisible(@Param("companyId") Long companyId, @Param("visible") Boolean visible);

    @Query("SELECT e FROM EmployeeEntity e WHERE e.branchEntity.companyEntity.idCompany = :companyId AND e.identificationNumber = :identificationNumber AND e.visible = :visible")
    Optional<EmployeeEntity> findEmployee(@Param("companyId") Long companyId, @Param("identificationNumber") String identificationNumber, @Param("visible") Boolean visible);

    @Query("""
                SELECT e FROM EmployeeEntity e
                WHERE e.branchEntity.companyEntity.idCompany = :companyId
                  AND (:includeRole6 = TRUE AND e.roleEntity.idRole = 6
                       OR :includeRole6 = FALSE AND e.roleEntity.idRole != 6)
                  AND e.visible = :visible
                  AND e.idEmployee != 1
            """)
    List<EmployeeEntity> findEmployeesByCompanyIdAndRoleCondition(@Param("companyId") Long companyId, @Param("visible") Boolean visible,
                                                                  @Param("includeRole6") Boolean includeRole6);

    @Query("""
                SELECT e FROM EmployeeEntity e
                WHERE e.branchEntity.idBranch = :branchId
                  AND (:includeRole6 = TRUE AND e.roleEntity.idRole = 6
                       OR :includeRole6 = FALSE AND e.roleEntity.idRole != 6)
                  AND e.visible = :visible
                  AND e.idEmployee != 1
            """)
    List<EmployeeEntity> findEmployeesByBranchIdAndRoleCondition(@Param("branchId") Long branchId, @Param("visible") Boolean visible,
                                                                 @Param("includeRole6") Boolean includeRole6);
}
