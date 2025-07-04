package com.interfija.masterposmultitenant.entities.tenant.company;

import com.interfija.masterposmultitenant.entities.tenant.other.IdentificationTypeEntity;
import com.interfija.masterposmultitenant.entities.tenant.other.MunicipalityEntity;
import com.interfija.masterposmultitenant.entities.tenant.other.TaxEntity;
import com.interfija.masterposmultitenant.entities.tenant.other.TypeOrganizationEntity;
import com.interfija.masterposmultitenant.entities.tenant.other.TypeRegimeEntity;
import com.interfija.masterposmultitenant.entities.tenant.other.TypeResponsibilityEntity;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
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
@Table(name = "company")
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_company")
    private Long idCompany;

    @Column(name = "identification_number", length = 15)
    private String identificationNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "identification_type_id", referencedColumnName = "id_identification_type", nullable = false)
    private IdentificationTypeEntity identificationTypeEntity;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "address", length = 100)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipality_id", referencedColumnName = "id_municipality")
    private MunicipalityEntity municipalityEntity;

    @Column(name = "mail", length = 100)
    private String mail;

    @Column(name = "phone", length = 50)
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

    @Lob
    @Column(name = "image", columnDefinition = "MEDIUMBLOB")
    @Basic(fetch = FetchType.LAZY)
    private byte[] image;

    @Lob
    @Column(name = "certificate", columnDefinition = "MEDIUMBLOB")
    @Basic(fetch = FetchType.LAZY)
    private byte[] certificate;

    @Column(name = "password_certificate", length = 15)
    private String passwordCertificate;

    @Column(name = "visible", columnDefinition = "BOOLEAN DEFAULT 1")
    private Boolean visible;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
