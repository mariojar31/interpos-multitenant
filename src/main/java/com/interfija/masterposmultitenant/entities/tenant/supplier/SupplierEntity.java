package com.interfija.masterposmultitenant.entities.tenant.supplier;

import com.interfija.masterposmultitenant.entities.tenant.branch.BranchEntity;
import com.interfija.masterposmultitenant.entities.tenant.other.IdentificationTypeEntity;
import com.interfija.masterposmultitenant.entities.tenant.other.MunicipalityEntity;
import com.interfija.masterposmultitenant.entities.tenant.other.TaxEntity;
import com.interfija.masterposmultitenant.entities.tenant.other.TypeOrganizationEntity;
import com.interfija.masterposmultitenant.entities.tenant.other.TypeRegimeEntity;
import com.interfija.masterposmultitenant.entities.tenant.other.TypeResponsibilityEntity;
import com.interfija.masterposmultitenant.entities.tenant.product.ProductBranchPriceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "supplier")
public class SupplierEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_supplier")
    private Long idSupplier;

    @Column(name = "identification_number", length = 15)
    private String identificationNumber;

    @Column(length = 100)
    private String names;

    @Column(name = "last_names", length = 100)
    private String lastNames;

    @Column(name = "credit_limit", precision = 15, scale = 6)
    private BigDecimal creditLimit;

    @Column(length = 100)
    private String address;

    @Column(length = 100)
    private String mail;

    @Column(length = 50)
    private String phone;

    @Column(name = "visible", columnDefinition = "BOOLEAN DEFAULT 1")
    private Boolean visible;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "identification_type_id")
    private IdentificationTypeEntity identificationTypeEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipality_id")
    private MunicipalityEntity municipalityEntity;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private BranchEntity branchEntity;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "supplierEntity", fetch = FetchType.LAZY)
    private List<ProductBranchPriceEntity> productPrices;

}
