package com.interfija.masterposmultitenant.entities.tenant.invoice;

import com.interfija.masterposmultitenant.entities.tenant.other.TypePaymentEntity;
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
@Table(name = "invoice_payment")
public class InvoicePaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_invoice_payment", nullable = false)
    private Long idInvoicePayment;

    @Column(name = "total_received", precision = 15, scale = 6, nullable = false)
    private BigDecimal totalReceived;

    @Column(name = "total_change", precision = 15, scale = 6)
    private BigDecimal totalChange;

    @ManyToOne
    @JoinColumn(name = "type_payment_id", referencedColumnName = "id_type_payment", nullable = false)
    private TypePaymentEntity typePaymentEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", referencedColumnName = "id_invoice", nullable = false)
    private InvoiceEntity invoiceEntity;

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
