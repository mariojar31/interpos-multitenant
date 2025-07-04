package com.interfija.masterposmultitenant.repository.cash.projections;

import java.math.BigDecimal;

public interface CashTaxProjection {

    Short getTypeTaxId();

    String getTypeTaxName();

    BigDecimal getTypeTaxRate();

    Long getInvoiceId();

    Long getCashId();

    BigDecimal getSaleSum();

    BigDecimal getTaxSum();

}
