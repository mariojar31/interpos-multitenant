package com.interfija.masterposmultitenant.repository.supplier;

import com.interfija.masterposmultitenant.entities.tenant.supplier.SupplierEntity;
import com.interfija.masterposmultitenant.repository.supplier.projections.SupplierProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, Long> {

    @Query("""
                SELECT
                    s.idSupplier AS idSupplier,
                    s.names AS names,
                    s.lastNames AS lastNames,
                    s.branchEntity.idBranch AS branchId,
                    s.branchEntity.name AS branchName
                FROM SupplierEntity s
                WHERE s.visible = :visible
            """)
    List<SupplierProjection> findAllProjectedBasicByVisible(@Param("visible") Boolean visible);

    @Query("""
                SELECT
                    s.idSupplier AS idSupplier,
                    s.names AS names,
                    s.lastNames AS lastNames,
                    s.phone AS phone,
                    s.identificationNumber AS identificationNumber,
                    s.branchEntity.idBranch AS branchId,
                    s.branchEntity.name AS branchName,
                    s.branchEntity.companyEntity.idCompany AS companyId,
                    s.branchEntity.companyEntity.name AS companyName,
                    COUNT(DISTINCT pbp.productBranchEntity.idProductBranch) AS productCount
                FROM SupplierEntity s
                LEFT JOIN s.productPrices pbp
                WHERE s.visible = :visible
                GROUP BY s.idSupplier, s.names, s.lastNames, s.phone, s.identificationNumber,
                         s.branchEntity.idBranch, s.branchEntity.name,
                         s.branchEntity.companyEntity.idCompany, s.branchEntity.companyEntity.name
            """)
    List<SupplierProjection> findAllProjectedSummaryByVisible(@Param("visible") Boolean visible);

    @Query("""
              SELECT
                    s.idSupplier AS idSupplier,
                    s.names AS names,
                    s.lastNames AS lastNames,
                    s.branchEntity.idBranch AS branchId,
                    s.branchEntity.name AS branchName
                FROM SupplierEntity s
                WHERE s.branchEntity.companyEntity.idCompany = :companyId AND s.visible = :visible
            """)
    List<SupplierProjection> findAllProjectedBasicByCompanyIdAndVisible(@Param("companyId") Long companyId, @Param("visible") Boolean visible);

    @Query("""
                SELECT
                    s.idSupplier AS idSupplier,
                    s.names AS names,
                    s.lastNames AS lastNames,
                    s.phone AS phone,
                    s.identificationNumber AS identificationNumber,
                    s.branchEntity.idBranch AS branchId,
                    s.branchEntity.name AS branchName,
                    s.branchEntity.companyEntity.idCompany AS companyId,
                    s.branchEntity.companyEntity.name AS companyName,
                    COUNT(DISTINCT pbp.productBranchEntity.idProductBranch) AS productCount
                FROM SupplierEntity s
                LEFT JOIN s.productPrices pbp
                WHERE s.branchEntity.companyEntity.idCompany = :companyId AND s.visible = :visible
                GROUP BY s.idSupplier, s.names, s.lastNames, s.phone, s.identificationNumber,
                         s.branchEntity.idBranch, s.branchEntity.name,
                         s.branchEntity.companyEntity.idCompany, s.branchEntity.companyEntity.name
            """)
    List<SupplierProjection> findAllProjectedSummaryByCompanyIdAndVisible(@Param("companyId") Long companyId, @Param("visible") Boolean visible);

}
