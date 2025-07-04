package com.interfija.masterposmultitenant.repository.product;

import com.interfija.masterposmultitenant.entities.tenant.product.ProductBranchPriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductBranchPriceRepository extends JpaRepository<ProductBranchPriceEntity, Long> {
}
