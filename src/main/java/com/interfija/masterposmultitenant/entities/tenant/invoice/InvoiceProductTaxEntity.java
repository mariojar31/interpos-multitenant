package com.interfija.masterposmultitenant.entities.tenant.invoice;

import com.interfija.masterposmultitenant.entities.tenant.other.TypeTaxEntity;
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
@Table(name = "invoice_product_tax")
public class InvoiceProductTaxEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_invoice_product_tax", nullable = false)
    private Long idInvoiceProductTax;

    @ManyToOne
    @JoinColumn(name = "type_tax_id", referencedColumnName = "id_type_tax", nullable = false)
    private TypeTaxEntity typeTaxEntity;

    @Column(name = "total_tax", precision = 15, scale = 6, nullable = false)
    private BigDecimal totalTax;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_product_id", referencedColumnName = "id_invoice_product", nullable = false)
    private InvoiceProductEntity invoiceProductEntity;

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
