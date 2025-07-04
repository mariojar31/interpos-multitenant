package com.interfija.masterposmultitenant.repository.floor;

import com.interfija.masterposmultitenant.entities.tenant.floor.FloorEntity;
import com.interfija.masterposmultitenant.repository.floor.projections.FloorProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FloorRepository extends JpaRepository<FloorEntity, Long> {

    @Query("""
                SELECT
                    f.idFloor AS idFloor,
                    f.name AS name,
                    f.branchEntity.idBranch AS branchId,
                    f.branchEntity.name AS branchName
                FROM FloorEntity f
                WHERE f.visible = :visible
            """)
    List<FloorProjection> findAllProjectedBasicByVisible(@Param("visible") Boolean visible);

    @Query("""
                SELECT
                    f.idFloor AS idFloor,
                    f.name AS name,
                    f.branchEntity.idBranch AS branchId,
                    f.branchEntity.name AS branchName,
                    f.branchEntity.companyEntity.idCompany AS companyId,
                    f.branchEntity.companyEntity.name AS companyName
                FROM FloorEntity f
                WHERE f.visible = :visible
            """)
    List<FloorProjection> findAllProjectedSummaryByVisible(@Param("visible") Boolean visible);

    @Query("""
                SELECT
                    f.idFloor AS idFloor,
                    f.name AS name,
                    f.branchEntity.idBranch AS branchId,
                    f.branchEntity.name AS branchName
                FROM FloorEntity f
                WHERE f.branchEntity.idBranch = :branchId AND f.visible = :visible
            """)
    List<FloorProjection> findAllProjectedBasicByBranchIdAndVisible(@Param("branchId") Long branchId, @Param("visible") Boolean visible);

    @Query("""
                SELECT
                    f.idFloor AS idFloor,
                    f.name AS name,
                    f.branchEntity.idBranch AS branchId,
                    f.branchEntity.name AS branchName,
                    f.branchEntity.companyEntity.idCompany AS companyId,
                    f.branchEntity.companyEntity.name AS companyName
                FROM FloorEntity f
                WHERE f.branchEntity.idBranch = :branchId AND f.visible = :visible
            """)
    List<FloorProjection> findAllProjectedSummaryByBranchIdAndVisible(@Param("branchId") Long branchId, @Param("visible") Boolean visible);

}
