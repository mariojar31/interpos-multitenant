package com.interfija.masterposmultitenant.repository.product.projections;

import java.math.BigDecimal;

public interface ProductBranchStockProjection {

    Long getIdProductBranch();

    BigDecimal getQuantity();

    Long getBranchId();

    String getBranchName();

    Short getTypeUnitId();

    String getTypeUnitName();

    String getTypeUnitAbbreviation();

    BigDecimal getTypeUnitBaseValue();

}
