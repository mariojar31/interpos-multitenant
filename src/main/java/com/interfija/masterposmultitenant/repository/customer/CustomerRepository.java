package com.interfija.masterposmultitenant.repository.customer;

import com.interfija.masterposmultitenant.entities.tenant.customer.CustomerEntity;
import com.interfija.masterposmultitenant.repository.customer.projections.CustomerProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    @Query("""
                SELECT
                    c.idCustomer AS idCustomer,
                    c.names AS names,
                    c.lastNames AS lastNames,
                    c.branchEntity.idBranch AS branchId,
                    c.branchEntity.name AS branchName
                FROM CustomerEntity c
                WHERE c.visible = :visible AND c.idCustomer != 1
            """)
    List<CustomerProjection> findAllProjectedBasicByVisible(@Param("visible") Boolean visible);

    @Query("""
                SELECT
                    c.idCustomer AS idCustomer,
                    c.names AS names,
                    c.lastNames AS lastNames,
                    c.phone AS phone,
                    c.identificationNumber AS identificationNumber,
                    c.branchEntity.idBranch AS branchId,
                    c.branchEntity.name AS branchName,
                    c.branchEntity.companyEntity.idCompany AS companyId,
                    c.branchEntity.companyEntity.name AS companyName
                FROM CustomerEntity c
                WHERE c.visible = :visible AND c.idCustomer != 1
            """)
    List<CustomerProjection> findAllProjectedSummaryByVisible(@Param("visible") Boolean visible);

    @Query("""
                SELECT
                    c.idCustomer AS idCustomer,
                    c.names AS names,
                    c.lastNames AS lastNames,
                    c.branchEntity.idBranch AS branchId,
                    c.branchEntity.name AS branchName
                FROM CustomerEntity c
                WHERE c.branchEntity.companyEntity.idCompany = :companyId AND c.visible = :visible AND c.idCustomer != 1
            """)
    List<CustomerProjection> findAllProjectedBasicByCompanyIdAndVisible(@Param("companyId") Long branchId, @Param("visible") Boolean visible);

    @Query("""
                SELECT
                    c.idCustomer AS idCustomer,
                    c.names AS names,
                    c.lastNames AS lastNames,
                    c.phone AS phone,
                    c.identificationNumber AS identificationNumber,
                    c.branchEntity.idBranch AS branchId,
                    c.branchEntity.name AS branchName,
                    c.branchEntity.companyEntity.idCompany AS companyId,
                    c.branchEntity.companyEntity.name AS companyName
                FROM CustomerEntity c
                WHERE c.branchEntity.companyEntity.idCompany = :companyId AND c.visible = :visible AND c.idCustomer != 1
            """)
    List<CustomerProjection> findAllProjectedSummaryByCompanyIdAndVisible(@Param("companyId") Long branchId, @Param("visible") Boolean visible);

}
