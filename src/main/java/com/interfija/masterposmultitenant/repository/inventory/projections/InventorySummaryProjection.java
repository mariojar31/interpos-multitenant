package com.interfija.masterposmultitenant.repository.inventory.projections;

import java.math.BigDecimal;

public interface InventorySummaryProjection {

    Long getIdProduct();

    String getName();

    String getReference();

    String getBarcode();

    String getCategoryName();

    Short getTypeUnitId();

    String getTypeUnitName();

    String getTypeUnitAbbreviation();

    BigDecimal getTypeUnitBaseValue();

    String getBranchName();

    String getCompanyName();

    BigDecimal getPurchasePrice();

    BigDecimal getSalePrice();

    BigDecimal getQuantity();

}
