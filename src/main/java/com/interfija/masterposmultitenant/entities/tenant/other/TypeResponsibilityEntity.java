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
@Table(name = "type_responsibility")
public class TypeResponsibilityEntity {

    @Id
    @Column(name = "id_type_responsibility")
    private Short idTypeResponsibility;

    @Column(nullable = false)
    private String name;

    @Column(length = 7, nullable = false, unique = true)
    private String code;

    @Column(name = "api_responsibility_id")
    private Short apiResponsibilityId;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
