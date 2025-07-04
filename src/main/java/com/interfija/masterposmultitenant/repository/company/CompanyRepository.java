package com.interfija.masterposmultitenant.repository.company;

import com.interfija.masterposmultitenant.entities.tenant.company.CompanyEntity;
import com.interfija.masterposmultitenant.repository.company.projections.CompanyProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {

    @Query("""
                SELECT
                    c.idCompany AS idCompany,
                    c.name AS name
                FROM CompanyEntity c
                WHERE c.visible = :visible
            """)
    List<CompanyProjection> findAllProjectedBasicByVisible(@Param("visible") Boolean visible);

    @Query("""
                SELECT
                    c.idCompany AS idCompany,
                    c.name AS name,
                    c.identificationNumber AS identificationNumber,
                    c.address AS address
                FROM CompanyEntity c
                WHERE c.visible = :visible
            """)
    List<CompanyProjection> findAllProjectedSummaryByVisible(@Param("visible") Boolean visible);

}
