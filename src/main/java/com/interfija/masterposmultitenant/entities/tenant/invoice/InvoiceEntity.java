package com.interfija.masterposmultitenant.entities.tenant.invoice;

import com.interfija.masterposmultitenant.entities.tenant.cash.CashEntity;
import com.interfija.masterposmultitenant.entities.tenant.other.PaymentFormEntity;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.interfija.masterposmultitenant.utils.StandardMethod.nowUtc;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "invoice")
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_invoice", nullable = false)
    private Long idInvoice;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "payment_form_id", referencedColumnName = "id_payment_form", nullable = false)
    private PaymentFormEntity paymentFormEntity;

    @Column(name = "days_term", nullable = false)
    private int daysTerm;

    @Column(name = "date_term")
    private LocalDate dateTerm;

    @Column(name = "value_service_change", precision = 2, scale = 1, nullable = false)
    private BigDecimal valueServiceChange;

    @Column(name = "commission_type", length = 1)
    private String commissionType;

    @Column(name = "value_commission", precision = 15, scale = 6)
    private BigDecimal valueCommission;

    @Column(name = "discount_type", length = 1)
    private String discountType;

    @Column(name = "value_discount", precision = 15, scale = 6, nullable = false)
    private BigDecimal valueDiscount;

    @Column(name = "subtotal", precision = 15, scale = 6, nullable = false)
    private BigDecimal subtotal;

    @Column(name = "total_discount", precision = 15, scale = 6, nullable = false)
    private BigDecimal totalDiscount;

    @Column(name = "total_service_change", precision = 15, scale = 6, nullable = false)
    private BigDecimal totalServiceChange;

    @Column(name = "total_tax", precision = 15, scale = 6, nullable = false)
    private BigDecimal totalTax;

    @Column(name = "total_retention", precision = 15, scale = 6, nullable = false)
    private BigDecimal totalRetention;

    @Column(name = "total", precision = 15, scale = 6, nullable = false)
    private BigDecimal total;

    @Column(name = "total_commission", precision = 15, scale = 6, nullable = false)
    private BigDecimal totalCommission;

    @Column(name = "table_name", length = 100)
    private String tableName;

    @Column(name = "observation", length = 1000)
    private String observation;

    @ManyToOne
    @JoinColumn(name = "cash_id", referencedColumnName = "id_cash", nullable = false)
    private CashEntity cashEntity;

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

    @OneToMany(mappedBy = "invoiceEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<InvoiceTaxEntity> taxes;

    @OneToMany(mappedBy = "invoiceEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<InvoiceEmployeeEntity> employees;

    @OneToMany(mappedBy = "invoiceEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<InvoiceCustomerEntity> customers;

    @OneToMany(mappedBy = "invoiceEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<InvoicePaymentEntity> payments;

    @OneToMany(mappedBy = "invoiceEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<InvoiceProductEntity> products;

    public InvoiceEntity(Long idInvoice) {
        this.idInvoice = idInvoice;
    }

    public void addTax(InvoiceTaxEntity tax) {
        taxes.add(tax);
        tax.setInvoiceEntity(this);
    }

    public void removeTax(InvoiceTaxEntity tax) {
        taxes.remove(tax);
    }

    public void addEmployee(InvoiceEmployeeEntity employee) {
        employees.add(employee);
        employee.setInvoiceEntity(this);
    }

    public void removeEmployee(InvoiceEmployeeEntity employee) {
        employees.remove(employee);
    }

    public void addCustomer(InvoiceCustomerEntity customer) {
        customers.add(customer);
        customer.setInvoiceEntity(this);
    }

    public void removeCustomer(InvoiceCustomerEntity customer) {
        customers.remove(customer);
    }

    public void addPayment(InvoicePaymentEntity payment) {
        payments.add(payment);
        payment.setInvoiceEntity(this);
    }

    public void removePayment(InvoicePaymentEntity payment) {
        payments.remove(payment);
    }

    public void addProduct(InvoiceProductEntity product) {
        products.add(product);
        product.setInvoiceEntity(this);
    }

    public void removeProduct(InvoiceProductEntity product) {
        products.remove(product);
    }

}
