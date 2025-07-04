package com.interfija.masterposmultitenant.entities.tenant.product;

import com.interfija.masterposmultitenant.entities.tenant.branch.BranchEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
import java.util.List;

import static com.interfija.masterposmultitenant.utils.StandardMethod.nowUtc;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_branch")
public class ProductBranchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product_branch")
    private Long idProductBranch;

    @Column(name = "printer_number")
    private Byte printerNumber;

    @Column(name = "commission_type", length = 1)
    private String commissionType;

    @Column(name = "value_commission", precision = 15, scale = 6)
    private BigDecimal valueCommission;

    @Column(name = "quantity", precision = 15, scale = 6)
    private BigDecimal quantity;

    @Column(name = "visible", columnDefinition = "BOOLEAN DEFAULT 1")
    private Boolean visible;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity productEntity;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private BranchEntity branchEntity;

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

    @OneToMany(mappedBy = "productBranchEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductBranchPriceEntity> prices;

    @OneToMany(mappedBy = "productBranchEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductBranchBatchEntity> batches;

    public void addPrice(ProductBranchPriceEntity price) {
        prices.add(price);
        price.setProductBranchEntity(this);
    }

    public void removePrice(ProductBranchPriceEntity price) {
        prices.remove(price);
    }

    public void addBatch(ProductBranchBatchEntity batch) {
        batches.add(batch);
        batch.setProductBranchEntity(this);
    }

    public void removeBatch(ProductBranchBatchEntity batch) {
        batches.remove(batch);
    }

}
