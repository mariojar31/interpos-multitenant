package com.interfija.masterposmultitenant.repository.sale;

import com.interfija.masterposmultitenant.entities.tenant.sale.SalePendingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalePendingRepository extends JpaRepository<SalePendingEntity, String> {

    @Query("SELECT COUNT(s) FROM SalePendingEntity s WHERE s.branchEntity.idBranch = :branchId")
    int findSalePendingCountByBranchId(@Param("branchId") Long branchId);

    @Query("SELECT s FROM SalePendingEntity s WHERE s.branchEntity.idBranch = :branchId")
    List<SalePendingEntity> findAllByBranchId(@Param("branchId") Long branchId);

    @Query("SELECT s FROM SalePendingEntity s WHERE s.branchEntity.idBranch = :branchId AND s.tableEntity.idTable IS NOT NULL")
    List<SalePendingEntity> findAllByTables(@Param("branchId") Long branchId);

    @Query("SELECT s.invoice FROM SalePendingEntity s WHERE s.idSalePending = :salePendingId")
    Optional<String> findInvoiceById(@Param("salePendingId") String salePendingId);

}
