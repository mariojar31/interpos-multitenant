package com.interfija.masterposmultitenant.repository.other;

import com.interfija.masterposmultitenant.entities.tenant.other.PaymentFormEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentFormRepository extends JpaRepository<PaymentFormEntity, Short> {}
