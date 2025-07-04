package com.interfija.masterposmultitenant.repository.product;

import com.interfija.masterposmultitenant.entities.tenant.product.ProductBranchEntity;
import com.interfija.masterposmultitenant.repository.product.projections.ProductBranchStockProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductBranchRepository extends JpaRepository<ProductBranchEntity, Long> {

    @Query("""
                SELECT s FROM ProductBranchEntity s
                JOIN FETCH s.productEntity p
                WHERE p.id = :productId AND s.branchEntity.id = :branchId
            """)
    List<ProductBranchEntity> findStockByProductAndBranch(@Param("productId") Long productId, @Param("branchId") Long branchId);

    @Query("""
                SELECT
                    s.idProductBranch as idProductBranch,
                    s.quantity AS quantity,
                    s.branchEntity.id AS branchId,
                    s.branchEntity.name AS branchName,
                    p.typeUnitEntity.id AS typeUnitId,
                    p.typeUnitEntity.name AS typeUnitName,
                    p.typeUnitEntity.abbreviation AS typeUnitAbbreviation,
                    p.typeUnitEntity.baseValue AS typeUnitBaseValue
                FROM ProductBranchEntity s
                JOIN s.productEntity p
                JOIN p.typeUnitEntity
                JOIN s.branchEntity
                WHERE p.id = :productId
            """)
    List<ProductBranchStockProjection> findStockByProductId(@Param("productId") Long productId);

    @Modifying
    @Transactional
    @Query("""
                UPDATE ProductBranchEntity s SET s.quantity = s.quantity + (:quantity) WHERE s.idProductBranch = :idProductBranch
            """)
    int updateStockProduct(@Param("quantity") BigDecimal quantity, @Param("idProductBranch") Long idProductBranch);

}
