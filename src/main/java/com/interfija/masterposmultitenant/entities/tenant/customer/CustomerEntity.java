package com.interfija.masterposmultitenant.entities.tenant.customer;

import com.interfija.masterposmultitenant.entities.tenant.branch.BranchEntity;
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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_customer")
    private Long idCustomer;

    @Column(name = "identification_number", length = 15)
    private String identificationNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "identification_type_id")
    private IdentificationTypeEntity identificationTypeEntity;

    @Column(name = "names", length = 100)
    private String names;

    @Column(name = "last_names", length = 100)
    private String lastNames;

    @Column(name = "address", length = 100)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipality_id")
    private MunicipalityEntity municipalityEntity;

    @Column(name = "mail", length = 100)
    private String mail;

    @Column(name = "phone", length = 50)
    private String phone;

    @Column(name = "debt_date")
    private LocalDateTime debtDate;

    @Column(name = "amount_debt", precision = 15, scale = 6)
    private BigDecimal amountDebt;

    @Column(name = "max_debt", nullable = false, precision = 15, scale = 6)
    private BigDecimal maxDebt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_regime_id")
    private TypeRegimeEntity typeRegimeEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_organization_id")
    private TypeOrganizationEntity typeOrganizationEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_responsibility_id")
    private TypeResponsibilityEntity typeResponsibilityEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tax_id")
    private TaxEntity taxEntity;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private BranchEntity branchEntity;

    @Column(name = "visible", columnDefinition = "BOOLEAN DEFAULT 1")
    private Boolean visible;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
