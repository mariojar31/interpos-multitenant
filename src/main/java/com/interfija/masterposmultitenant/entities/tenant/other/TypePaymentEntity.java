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
@Table(name = "type_payment")
public class TypePaymentEntity {

    @Id
    @Column(name = "id_type_payment")
    private Short idTypePayment;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "code", length = 2)
    private String code;

    @Column(name = "api_payment_id")
    private Short apiPaymentId;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
