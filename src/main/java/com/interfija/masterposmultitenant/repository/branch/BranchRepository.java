package com.interfija.masterposmultitenant.repository.branch;

import com.interfija.masterposmultitenant.entities.tenant.branch.BranchEntity;
import com.interfija.masterposmultitenant.repository.branch.projections.BranchProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<BranchEntity, Long> {

    @Query("""
                SELECT
                    b.idBranch AS idBranch,
                    b.name AS name,
                    b.companyEntity.idCompany AS companyId,
                    b.companyEntity.name AS companyName
                FROM BranchEntity b
                WHERE b.visible = :visible
            """)
    List<BranchProjection> findAllProjectedBasicByVisible(@Param("visible") Boolean visible);

    @Query("""
                SELECT
                    b.idBranch AS idBranch,
                    b.name AS name,
                    b.address AS address,
                    b.companyEntity.idCompany AS companyId,
                    b.companyEntity.name AS companyName
                FROM BranchEntity b
                WHERE b.visible = :visible
            """)
    List<BranchProjection> findAllProjectedSummaryByVisible(@Param("visible") Boolean visible);

    @Query("""
                SELECT
                    b.idBranch AS idBranch,
                    b.name AS name,
                    b.companyEntity.idCompany AS companyId,
                    b.companyEntity.name AS companyName
                FROM BranchEntity b
                WHERE b.companyEntity.idCompany = :companyId AND b.visible = :visible
            """)
    List<BranchProjection> findAllProjectedBasicByCompanyIdAndVisible(@Param("companyId") Long companyId, @Param("visible") Boolean visible);

    @Query("""
                SELECT
                    b.idBranch AS idBranch,
                    b.name AS name,
                    b.address AS address,
                    b.companyEntity.idCompany AS companyId,
                    b.companyEntity.name AS companyName
                FROM BranchEntity b
                WHERE b.companyEntity.idCompany = :companyId AND b.visible = :visible
            """)
    List<BranchProjection> findAllProjectedSummaryByCompanyIdAndVisible(@Param("companyId") Long companyId, @Param("visible") Boolean visible);

}
