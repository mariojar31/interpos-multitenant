package com.interfija.masterposmultitenant.repository.category;

import com.interfija.masterposmultitenant.entities.tenant.category.CategoryEntity;
import com.interfija.masterposmultitenant.repository.category.projections.CategoryProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    @Query("""
                SELECT
                    c.idCategory AS idCategory,
                    c.name AS name,
                    c.code AS code,
                    c.branchEntity.idBranch AS branchId,
                    c.branchEntity.name AS branchName,
                    parent.idCategory AS parentId,
                    parent.name AS parentName
                FROM CategoryEntity c
                LEFT JOIN c.parent parent
                WHERE c.visible = :visible
            """)
    List<CategoryProjection> findAllProjectedBasicByVisible(@Param("visible") Boolean visible);

    @Query("""
                SELECT
                    c.idCategory AS idCategory,
                    c.name AS name,
                    c.code AS code,
                    COUNT(DISTINCT p) AS productCount,
                    COUNT(DISTINCT child) AS childrenCount,
                    c.branchEntity.idBranch AS branchId,
                    c.branchEntity.name AS branchName,
                    c.branchEntity.companyEntity.idCompany AS companyId,
                    c.branchEntity.companyEntity.name AS companyName,
                    parent.idCategory AS parentId,
                    parent.name AS parentName
                FROM CategoryEntity c
                LEFT JOIN c.parent parent
                LEFT JOIN c.products p
                LEFT JOIN CategoryEntity child ON child.parent = c
                WHERE c.visible = :visible
                GROUP BY c
            """)
    List<CategoryProjection> findAllProjectedSummaryByVisible(@Param("visible") Boolean visible);

    @Query("""
              SELECT
                    c.idCategory AS idCategory,
                    c.name AS name,
                    c.code AS code,
                    c.branchEntity.idBranch AS branchId,
                    c.branchEntity.name AS branchName,
                    parent.idCategory AS parentId,
                    parent.name AS parentName
                FROM CategoryEntity c
                LEFT JOIN c.parent parent
                WHERE c.branchEntity.companyEntity.idCompany = :companyId AND c.visible = :visible
            """)
    List<CategoryProjection> findAllProjectedBasicByCompanyIdAndVisible(@Param("companyId") Long companyId, @Param("visible") Boolean visible);

    @Query("""
                SELECT
                    c.idCategory AS idCategory,
                    c.name AS name,
                    c.code AS code,
                    COUNT(DISTINCT p) AS productCount,
                    COUNT(DISTINCT child) AS childrenCount,
                    c.branchEntity.idBranch AS branchId,
                    c.branchEntity.name AS branchName,
                    c.branchEntity.companyEntity.idCompany AS companyId,
                    c.branchEntity.companyEntity.name AS companyName,
                    parent.idCategory AS parentId,
                    parent.name AS parentName
                FROM CategoryEntity c
                LEFT JOIN c.parent parent
                LEFT JOIN c.products p
                LEFT JOIN CategoryEntity child ON child.parent = c
                WHERE c.branchEntity.companyEntity.idCompany = :companyId AND c.visible = :visible
                GROUP BY c
            """)
    List<CategoryProjection> findAllProjectedSummaryByCompanyIdAndVisible(@Param("companyId") Long companyId, @Param("visible") Boolean visible);

}
