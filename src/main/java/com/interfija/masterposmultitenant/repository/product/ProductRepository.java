package com.interfija.masterposmultitenant.repository.product;

import com.interfija.masterposmultitenant.entities.tenant.product.ProductEntity;
import com.interfija.masterposmultitenant.repository.inventory.projections.InventorySummaryProjection;
import com.interfija.masterposmultitenant.repository.product.projections.ProductProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("""
                SELECT
                    p.idProduct AS idProduct,
                    p.name AS name,
                    p.reference AS reference,
                    p.barcode AS barcode,
                    p.categoryEntity.name AS categoryName,
                    tu.idTypeUnit AS typeUnitId,
                    tu.name AS typeUnitName,
                    tu.abbreviation AS typeUnitAbbreviation,
                    tu.baseValue AS typeUnitBaseValue,
                    pb.quantity AS quantity,
                    pb.branchEntity.name AS branchName,
                    pb.branchEntity.companyEntity.name AS companyName,
                    price.purchasePrice AS purchasePrice,
                    price.salePrice AS salePrice
                FROM ProductEntity p
                JOIN p.branches pb
                JOIN pb.prices price
                JOIN p.typeUnitEntity tu
                WHERE pb.branchEntity.idBranch = :branchId
                  AND price.defaultPrice = true
            """)
    List<InventorySummaryProjection> findAllByBranchIdAndDefaultPrice(@Param("branchId") Long branchId);

    @Query("""
                SELECT DISTINCT p FROM ProductEntity p
                JOIN FETCH p.branches s
                WHERE s.branchEntity.idBranch = :branchId AND s.visible = :visible
            """)
    List<ProductEntity> findAllByBranchId(@Param("branchId") Long branchId, @Param("visible") Boolean visible);

    @Query("""
                SELECT DISTINCT p FROM ProductEntity p
                JOIN p.branches s
                WHERE s.branchEntity.companyEntity.idCompany = :companyId
            """)
    List<ProductEntity> findAllByCompanyId(@Param("companyId") Long companyId);

    @Query("""
            SELECT
                p.idProduct AS idProduct,
                p.name AS name,
                p.reference AS reference,
                p.barcode AS barcode,
                p.categoryEntity.idCategory AS categoryId,
                p.categoryEntity.name AS categoryName,
                p.typeUnitEntity.id AS typeUnitId,
                p.typeUnitEntity.name AS typeUnitName,
                p.typeUnitEntity.abbreviation AS typeUnitAbbreviation,
                p.typeUnitEntity.baseValue AS typeUnitBaseValue,
                pb.branchEntity.idBranch AS branchId,
                pb.branchEntity.name AS branchName,
                pb.branchEntity.companyEntity.idCompany AS companyId,
                pb.branchEntity.companyEntity.name AS companyName
            FROM ProductEntity p
            JOIN p.branches pb
            LEFT JOIN pb.batches b
            JOIN p.typeUnitEntity tu
            WHERE pb.branchEntity.companyEntity.idCompany = :companyId
              AND pb.visible = :visibleOnly
            
                AND (
                    :expiredOnly = false OR EXISTS (
                        SELECT 1 FROM ProductBranchBatchEntity bb
                        WHERE bb.productBranchEntity = pb AND bb.expirationDate <= :expirationDate
                    )
                )
            
                AND (
                    :inventoryControl = false OR (
                        (
                            tu.idTypeUnit = 1 AND pb.quantity <= :minUni AND p.service = false
                        )
                        OR (
                            tu.idTypeUnit = 2 AND pb.quantity <= :minKg AND p.service = false
                        )
                        OR (
                            tu.idTypeUnit = 3 AND pb.quantity <= :minL AND p.service = false
                        )
                        OR (
                            tu.idTypeUnit = 4 AND pb.quantity <= :minM AND p.service = false
                        )
                    )
                )
            
                ORDER BY p.name
            """)
    List<ProductProjection> findAllByCompanyIdAndFilters(
            @Param("companyId") Long companyId,
            @Param("visibleOnly") Boolean visibleOnly,
            @Param("expiredOnly") Boolean expiredOnly,
            @Param("inventoryControl") Boolean inventoryControl,
            @Param("expirationDate") LocalDate expirationDate,
            @Param("minUni") BigDecimal minUni,
            @Param("minKg") BigDecimal minKg,
            @Param("minL") BigDecimal minL,
            @Param("minM") BigDecimal minM
    );

    @Query("""
                SELECT p FROM ProductEntity p
                JOIN FETCH p.branches s
                WHERE p.id = :productId AND s.branchEntity.id = :branchId
            """)
    ProductEntity findProductWithStockInBranch(@Param("productId") Long productId, @Param("branchId") Long branchId);

    @Query("""
                SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END
                FROM ProductEntity p
                WHERE LOWER(p.name) = LOWER(:name)
                AND (:idProduct IS NULL OR p.idProduct <> :idProduct)
            """)
    boolean existsByNameExcludingId(String name, Long idProduct);

    @Query("""
                SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END
                FROM ProductEntity p
                WHERE LOWER(p.reference) = LOWER(:reference)
                AND (:idProduct IS NULL OR p.idProduct <> :idProduct)
            """)
    boolean existsByReferenceExcludingId(String reference, Long idProduct);

    @Query("""
                SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END
                FROM ProductEntity p
                WHERE LOWER(p.barcode) = LOWER(:barcode)
                AND (:idProduct IS NULL OR p.idProduct <> :idProduct)
            """)
    boolean existsByBarcodeExcludingId(String barcode, Long idProduct);

}
