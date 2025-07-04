package com.interfija.masterposmultitenant.repository.invoice;

import com.interfija.masterposmultitenant.entities.tenant.invoice.InvoiceProductEntity;
import com.interfija.masterposmultitenant.repository.invoice.projections.CommissionProductProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InvoiceProductRepository extends JpaRepository<InvoiceProductEntity, Long> {

    @Query("""
                SELECT
                    i.date AS date,
                    ic.names AS customerNames,
                    ic.lastNames AS customerLastNames,
                    e.names AS employeeNames,
                    e.lastNames AS employeeLastNames,
                    ip.barcode AS barcode,
                    ip.name AS productName,
                    ip.salePrice AS salePrice,
                    tu.idTypeUnit AS typeUnitId,
                    tu.name AS typeUnitName,
                    tu.abbreviation AS typeUnitAbbreviation,
                    tu.baseValue AS typeUnitBaseValue,
                    ip.quantity AS quantity,
                    ip.commissionType AS commissionType,
                    ip.valueCommission AS valueCommission,
                    b.name AS branchName,
                    cp.name AS companyName,
                    ip.totalCommission AS totalCommission
                FROM InvoiceProductEntity ip
                JOIN ip.employees ipe
                JOIN ipe.employeeEntity e
                JOIN ip.typeUnitEntity tu
                JOIN ip.invoiceEntity i
                JOIN i.cashEntity c
                JOIN c.terminalEntity t
                JOIN t.floorEntity f
                JOIN f.branchEntity b
                JOIN b.companyEntity cp
                LEFT JOIN i.customers ic
                WHERE b.idBranch = :branchId AND i.date BETWEEN :startDate AND :endDate AND e.roleEntity.idRole != 6
            """)
    List<CommissionProductProjection> findAllCommissionSummaryByBranchId(@Param("branchId") Long branchId,
                                                                         @Param("startDate") LocalDateTime startDate,
                                                                         @Param("endDate") LocalDateTime endDate);

}
