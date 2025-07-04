package com.interfija.masterposmultitenant.repository.invoice;

import com.interfija.masterposmultitenant.entities.tenant.invoice.InvoiceEntity;
import com.interfija.masterposmultitenant.repository.invoice.projections.CommissionInvoiceManualProjection;
import com.interfija.masterposmultitenant.repository.sale.projections.SaleSummaryProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {

    @Query("""
                SELECT
                    i.idInvoice AS idInvoice,
                    i.date AS date,
                    e.names AS employeeNames,
                    e.lastNames AS employeeLastNames,
                    ic.names AS customerNames,
                    ic.lastNames AS customerLastNames,
                    i.total AS total
                FROM InvoiceEntity i
                JOIN i.employees ie
                JOIN ie.employeeEntity e
                JOIN i.cashEntity ca
                JOIN ca.terminalEntity te
                JOIN te.floorEntity fl
                LEFT JOIN i.customers ic
                WHERE fl.branchEntity.idBranch = :branchId
                  AND i.date BETWEEN :startDate AND :endDate
                  AND e.roleEntity.idRole != 6
                ORDER BY i.date DESC
            """)
    List<SaleSummaryProjection> findSaleSummaries(@Param("branchId") Long branchId,
                                                  @Param("startDate") LocalDateTime startDate,
                                                  @Param("endDate") LocalDateTime endDate);

    @Query("""
                SELECT i FROM InvoiceEntity i
                WHERE i.cashEntity.terminalEntity.floorEntity.branchEntity.idBranch = :branchId
                AND i.date BETWEEN :startDate AND :endDate
            """)
    List<InvoiceEntity> findInvoicesByDates(@Param("branchId") Long branchId,
                                            @Param("startDate") LocalDateTime startDate,
                                            @Param("endDate") LocalDateTime endDate);


    @Query("""
                SELECT
                    i.date AS date,
                    ic.names AS customerNames,
                    ic.lastNames AS customerLastNames,
                    ic.phone AS customerPhone,
                    e.names AS employeeNames,
                    e.lastNames AS employeeLastNames,
                    i.subtotal AS subtotal,
                    i.total AS total,
                    i.commissionType AS commissionType,
                    i.valueCommission AS valueCommission,
                    b.name AS branchName,
                    cp.name AS companyName,
                    i.totalCommission AS totalCommission
                FROM InvoiceEntity i
                JOIN i.employees ipe
                JOIN ipe.employeeEntity e
                JOIN i.cashEntity c
                JOIN c.terminalEntity t
                JOIN t.floorEntity f
                JOIN f.branchEntity b
                JOIN b.companyEntity cp
                LEFT JOIN i.customers ic
                WHERE b.idBranch = :branchId AND i.date BETWEEN :startDate AND :endDate
                AND e.roleEntity.idRole != 6 AND i.commissionType IS NOT NULL
            """)
    List<CommissionInvoiceManualProjection> findAllCommissionInvoiceManualSummaryByBranchId(@Param("branchId") Long branchId,
                                                                                            @Param("startDate") LocalDateTime startDate,
                                                                                            @Param("endDate") LocalDateTime endDate);
}
