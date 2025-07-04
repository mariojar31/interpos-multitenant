package com.interfija.masterposmultitenant.repository.invoice.projections;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface CommissionProductProjection {

    LocalDateTime getDate();

    String getCustomerNames();

    String getCustomerLastNames();

    String getEmployeeNames();

    String getEmployeeLastNames();

    String getBarcode();

    String getProductName();

    BigDecimal getSalePrice();

    Short getTypeUnitId();

    String getTypeUnitName();

    String getTypeUnitAbbreviation();

    BigDecimal getTypeUnitBaseValue();

    BigDecimal getQuantity();

    String getCommissionType();

    BigDecimal getValueCommission();

    String getBranchName();

    String getCompanyName();

    BigDecimal getTotalCommission();

}
