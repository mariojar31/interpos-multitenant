package com.interfija.masterposmultitenant.entities.tenant.branch;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "branch_configuration")
public class BranchConfigurationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_branch_configuration")
    private Long idBranchConfiguration;

    @Column(name = "prefix_invoice", length = 4)
    private String prefixInvoice;

    @Column(name = "header_invoice", length = 1000)
    private String headerInvoice;

    @Column(name = "footer_invoice", length = 1000)
    private String footerInvoice;

    @Column(name = "code_override")
    private String codeOverride;

    @Column(name = "value_service_change")
    private Integer valueServiceChange;

    @OneToOne
    @JoinColumn(name = "branch_id", referencedColumnName = "id_branch", nullable = false)
    private BranchEntity branchEntity;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
