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

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "type_unit")
public class TypeUnitEntity {

    @Id
    @Column(name = "id_type_unit")
    private Short idTypeUnit;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 10)
    private String abbreviation;

    @Column(name = "base_name")
    private String baseName;

    @Column(name = "base_value", nullable = false, precision = 15, scale = 6)
    private BigDecimal baseValue;

    @Column(name = "api_unit_id")
    private Short apiUnitId;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
