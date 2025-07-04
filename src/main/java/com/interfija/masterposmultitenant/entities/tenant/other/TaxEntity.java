package com.interfija.masterposmultitenant.entities.tenant.other;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "tax")
public class TaxEntity {

    @Id
    @Column(name = "id_tax")
    private Short idTax;

    @Column(nullable = false)
    private String name;

    @Column(length = 2, nullable = false, unique = true)
    private String code;

    @Column(name = "api_tax_id")
    private Short apiTaxId;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
