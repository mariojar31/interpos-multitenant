package com.interfija.masterposmultitenant.repository.product;

import com.interfija.masterposmultitenant.entities.tenant.product.ProductTaxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductTaxRepository extends JpaRepository<ProductTaxEntity, Long> {

    @Query("""
                SELECT pt FROM ProductTaxEntity pt
                WHERE pt.productEntity.idProduct = :productId
            """)
    List<ProductTaxEntity> findAllByProductId(@Param("productId") Long productId);

}
