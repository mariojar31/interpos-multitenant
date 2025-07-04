package com.interfija.masterposmultitenant.entities.tenant.product;

import com.interfija.masterposmultitenant.entities.tenant.other.PriceConditionEntity;
import com.interfija.masterposmultitenant.entities.tenant.other.PriceTypeEntity;
import com.interfija.masterposmultitenant.entities.tenant.supplier.SupplierEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.interfija.masterposmultitenant.utils.StandardMethod.nowUtc;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_branch_price")
public class ProductBranchPriceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product_branch_price")
    private Long idProductBranchPrice;

    @Column(name = "minimum_quantity", precision = 15, scale = 6)
    private BigDecimal minimumQuantity;

    @Column(name = "purchase_price", precision = 15, scale = 6)
    private BigDecimal purchasePrice;

    @Column(name = "sale_price", precision = 15, scale = 6)
    private BigDecimal salePrice;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "price_type_id")
    private PriceTypeEntity priceTypeEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "price_condition_id")
    private PriceConditionEntity priceConditionEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_branch_id", nullable = false)
    private ProductBranchEntity productBranchEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private SupplierEntity supplierEntity;

    @Column(name = "default_price", columnDefinition = "BOOLEAN DEFAULT 0")
    private Boolean defaultPrice;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = nowUtc();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = nowUtc();
    }
}
