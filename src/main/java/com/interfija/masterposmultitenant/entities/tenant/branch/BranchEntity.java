package com.interfija.masterposmultitenant.entities.tenant.branch;

import com.interfija.masterposmultitenant.entities.tenant.company.CompanyEntity;
import com.interfija.masterposmultitenant.entities.tenant.other.MunicipalityEntity;
import com.interfija.masterposmultitenant.entities.tenant.other.TypeOrganizationEntity;
import com.interfija.masterposmultitenant.entities.tenant.other.TypeRegimeEntity;
import com.interfija.masterposmultitenant.entities.tenant.other.TypeResponsibilityEntity;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "branch")
public class BranchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_branch")
    private Long idBranch;

    @Column(name = "branch_code", length = 10)
    private String branchCode;

    @Column(length = 100)
    private String name;

    @Column(length = 100)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipality_id", referencedColumnName = "id_municipality")
    private MunicipalityEntity municipalityEntity;

    @Column(length = 100)
    private String mail;

    @Column(length = 50)
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

    @Lob
    @Column(name = "image", columnDefinition = "MEDIUMBLOB")
    @Basic(fetch = FetchType.LAZY)
    private byte[] image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "id_company", nullable = false)
    private CompanyEntity companyEntity;

    @Column(name = "visible", columnDefinition = "BOOLEAN DEFAULT 1")
    private Boolean visible;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "branchEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private BranchConfigurationEntity branchConfiguration;

}
