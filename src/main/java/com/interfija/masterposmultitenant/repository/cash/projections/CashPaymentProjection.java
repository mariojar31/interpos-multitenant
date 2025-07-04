package com.interfija.masterposmultitenant.repository.cash.projections;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface CashPaymentProjection {

    Long getInvoicePaymentId();

    BigDecimal getTotalReceived();

    Short getTypePaymentId();

    String getTypePaymentName();

    LocalDateTime getInvoiceDate();

    Long getInvoiceId();

    Long getCashId();

}
