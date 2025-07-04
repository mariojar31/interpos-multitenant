package com.interfija.masterposmultitenant.entities.tenant.employee;

import com.interfija.masterposmultitenant.entities.tenant.branch.BranchEntity;
import com.interfija.masterposmultitenant.entities.tenant.other.IdentificationTypeEntity;
import com.interfija.masterposmultitenant.entities.tenant.other.MunicipalityEntity;
import com.interfija.masterposmultitenant.entities.tenant.role.RoleEntity;
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

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_employee")
    private Long idEmployee;

    @Column(name = "identification_number", length = 15, nullable = false, unique = true)
    private String identificationNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "identification_type_id")
    private IdentificationTypeEntity identificationTypeEntity;

    @Column(length = 100)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipality_id")
    private MunicipalityEntity municipalityEntity;

    @Column(length = 100, nullable = false)
    private String names;

    @Column(name = "last_names", length = 100)
    private String lastNames;

    @Column(length = 100, unique = true)
    private String mail;

    @Column(length = 100, unique = true)
    private String phone;

    @Column(length = 100)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity roleEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private BranchEntity branchEntity;

    @Column(name = "visible", columnDefinition = "BOOLEAN DEFAULT 1")
    private Boolean visible;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
