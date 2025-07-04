package com.interfija.masterposmultitenant.entities.tenant.invoice;

import com.interfija.masterposmultitenant.entities.tenant.other.TypeUnitEntity;
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
@Table(name = "invoice_product")
public class InvoiceProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_invoice_product", nullable = false)
    private Long idInvoiceProduct;

    @Column(name = "reference", nullable = false, length = 50)
    private String reference;

    @Column(name = "barcode", nullable = false, length = 50)
    private String barcode;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "purchase_price", precision = 15, scale = 6)
    private BigDecimal purchasePrice;

    @Column(name = "sale_price", precision = 15, scale = 6, nullable = false)
    private BigDecimal salePrice;

    @Column(name = "final_price", precision = 15, scale = 6, nullable = false)
    private BigDecimal finalPrice;

    @Column(name = "quantity", precision = 15, scale = 6, nullable = false)
    private BigDecimal quantity;

    @Column(name = "category_name", length = 100)
    private String categoryName;

    @Column(name = "discount_type", length = 1)
    private String discountType;

    @Column(name = "value_discount", precision = 15, scale = 6)
    private BigDecimal valueDiscount;

    @Column(name = "commission_type", length = 1)
    private String commissionType;

    @Column(name = "value_commission", precision = 15, scale = 6)
    private BigDecimal valueCommission;

    @Column(name = "subtotal", precision = 15, scale = 6, nullable = false)
    private BigDecimal subtotal;

    @Column(name = "total_discount", precision = 15, scale = 6, nullable = false)
    private BigDecimal totalDiscount;

    @Column(name = "total_tax", precision = 15, scale = 6, nullable = false)
    private BigDecimal totalTax;

    @Column(name = "total_retention", precision = 15, scale = 6, nullable = false)
    private BigDecimal totalRetention;

    @Column(name = "total", precision = 15, scale = 6, nullable = false)
    private BigDecimal total;

    @Column(name = "total_commission", precision = 15, scale = 6, nullable = false)
    private BigDecimal totalCommission;

    @Column(name = "variable_price", columnDefinition = "BOOLEAN DEFAULT 0")
    private Boolean variablePrice;

    @Column(name = "is_service", columnDefinition = "BOOLEAN DEFAULT 0")
    private Boolean service;

    @Column(name = "is_bundle", columnDefinition = "BOOLEAN DEFAULT 0")
    private Boolean bundle;

    @Column(name = "observation", length = 1000)
    private String observation;

    @ManyToOne
    @JoinColumn(name = "type_unit_id", referencedColumnName = "id_type_unit", nullable = false)
    private TypeUnitEntity typeUnitEntity;

    @Column(name = "product_stock_id")
    private Long productStockId;

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

    @OneToMany(mappedBy = "invoiceProductEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<InvoiceProductEmployeeEntity> employees;

    @OneToMany(mappedBy = "invoiceProductEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<InvoiceProductTaxEntity> taxes;

    public InvoiceProductEntity(Long idInvoiceProduct) {
        this.idInvoiceProduct = idInvoiceProduct;
    }

    public void addEmployee(InvoiceProductEmployeeEntity employee) {
        employees.add(employee);
        employee.setInvoiceProductEntity(this);
    }

    public void removeEmployee(InvoiceProductEmployeeEntity employee) {
        employees.remove(employee);
    }

    public void addTax(InvoiceProductTaxEntity tax) {
        taxes.add(tax);
        tax.setInvoiceProductEntity(this);
    }

    public void removeTax(InvoiceProductTaxEntity tax) {
        taxes.remove(tax);
    }

}
