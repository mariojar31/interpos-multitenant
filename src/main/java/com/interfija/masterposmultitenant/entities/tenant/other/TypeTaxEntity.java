package com.interfija.masterposmultitenant.entities.tenant.other;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "type_tax")
public class TypeTaxEntity {

    @Id
    @Column(name = "id_type_tax")
    private Short idTypeTax;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, precision = 15, scale = 6)
    private BigDecimal rate;

    @ManyToOne
    @JoinColumn(name = "tax_id", nullable = false)
    private TaxEntity taxEntity;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
