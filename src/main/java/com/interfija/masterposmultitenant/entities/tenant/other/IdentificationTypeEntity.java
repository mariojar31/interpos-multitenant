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
@Table(name = "identification_type")
public class IdentificationTypeEntity {

    @Id
    @Column(name = "id_identification_type")
    private Short idIdentificationType;

    @Column(nullable = false)
    private String name;

    @Column(length = 2, nullable = false, unique = true)
    private String code;

    @Column(length = 10, nullable = false)
    private String abbreviation;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
