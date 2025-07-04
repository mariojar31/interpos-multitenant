package com.interfija.masterposmultitenant.entities.tenant.invoice;

import com.interfija.masterposmultitenant.entities.tenant.other.IdentificationTypeEntity;
import com.interfija.masterposmultitenant.entities.tenant.other.MunicipalityEntity;
import com.interfija.masterposmultitenant.entities.tenant.other.TaxEntity;
import com.interfija.masterposmultitenant.entities.tenant.other.TypeOrganizationEntity;
import com.interfija.masterposmultitenant.entities.tenant.other.TypeRegimeEntity;
import com.interfija.masterposmultitenant.entities.tenant.other.TypeResponsibilityEntity;
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

import java.time.LocalDateTime;

import static com.interfija.masterposmultitenant.utils.StandardMethod.nowUtc;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "invoice_customer")
public class InvoiceCustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_invoice_customer", nullable = false)
    private Long idInvoiceCustomer;

    @Column(name = "identification_number", length = 15)
    private String identificationNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "identification_type_id", referencedColumnName = "id_identification_type")
    private IdentificationTypeEntity identificationTypeEntity;

    @Column(name = "names", nullable = false, length = 100)
    private String names;

    @Column(name = "last_names", length = 100)
    private String lastNames;

    @Column(name = "address", length = 100)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipality_id", referencedColumnName = "id_municipality")
    private MunicipalityEntity municipalityEntity;

    @Column(name = "mail", length = 100)
    private String mail;

    @Column(name = "phone", length = 15)
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_regime_id", referencedColumnName = "id_type_regime")
    private TypeRegimeEntity typeRegimeEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_organization_id", referencedColumnName = "id_type_organization")
    private TypeOrganizationEntity typeOrganizationEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_responsibility_id", referencedColumnName = "id_type_responsibility")
    private TypeResponsibilityEntity typeResponsibilityEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tax_id")
    private TaxEntity taxEntity;

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
