package com.interfija.masterposmultitenant.entities.tenant.product;

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
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.interfija.masterposmultitenant.utils.StandardMethod.nowUtc;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_branch_batch")
public class ProductBranchBatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product_branch_batch")
    private Long idProductBranchBatch;

    @Column(name = "batch_code", length = 20)
    private String batchCode;

    @Column(name = "serial_number", length = 20)
    private String serialNumber;

    @Column(name = "quantity", precision = 15, scale = 6)
    private BigDecimal quantity;

    @Column(name = "entry_date")
    private LocalDate entryDate;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "expedition_date")
    private LocalDate expeditionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_branch_id")
    private ProductBranchEntity productBranchEntity;

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
