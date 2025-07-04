package com.interfija.masterposmultitenant.repository.product;

import com.interfija.masterposmultitenant.entities.tenant.product.ProductBranchBatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductBranchBatchRepository extends JpaRepository<ProductBranchBatchEntity, Long> {
}
