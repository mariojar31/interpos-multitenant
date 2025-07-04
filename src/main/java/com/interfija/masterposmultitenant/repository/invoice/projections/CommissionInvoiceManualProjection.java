package com.interfija.masterposmultitenant.repository.invoice.projections;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface CommissionInvoiceManualProjection {

    LocalDateTime getDate();

    String getCustomerNames();

    String getCustomerLastNames();

    String getCustomerPhone();

    String getEmployeeNames();

    String getEmployeeLastNames();

    BigDecimal getSubtotal();

    BigDecimal getTotal();

    String getCommissionType();

    BigDecimal getValueCommission();

    String getBranchName();

    String getCompanyName();

    BigDecimal getTotalCommission();

}
