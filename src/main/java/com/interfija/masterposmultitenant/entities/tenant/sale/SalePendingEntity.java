package com.interfija.masterposmultitenant.entities.tenant.sale;

import com.interfija.masterposmultitenant.entities.tenant.branch.BranchEntity;
import com.interfija.masterposmultitenant.entities.tenant.floor.TableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "sales_pending")
public class SalePendingEntity {

    @Id
    @Column(name = "id_sale_pending", length = 36, nullable = false)
    private String idSalePending;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "employee_name", length = 100, nullable = false)
    private String employeeName;

    @Column(name = "customer_name", length = 100)
    private String customerName;

    @Column(name = "customer_phone", length = 15)
    private String customerPhone;

    @Column(name = "customer_address", length = 150)
    private String customerAddress;

    @Column(name = "domiciliary_name", length = 100)
    private String domiciliaryName;

    @Column(name = "invoice", nullable = false, columnDefinition = "TEXT")
    private String invoice; // JSON como un String

    @Column(name = "total", precision = 15, scale = 6, nullable = false)
    private BigDecimal total;

    @Column(name = "is_delivery", nullable = false, columnDefinition = "BOOLEAN DEFAULT 0")
    private Boolean delivery;

    @ManyToOne
    @JoinColumn(name = "table_id", referencedColumnName = "id_table")
    private TableEntity tableEntity;

    @ManyToOne
    @JoinColumn(name = "branch_id", referencedColumnName = "id_branch", nullable = false)
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

}
