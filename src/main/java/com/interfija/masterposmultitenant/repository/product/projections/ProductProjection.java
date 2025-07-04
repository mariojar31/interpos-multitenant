package com.interfija.masterposmultitenant.repository.product.projections;

import java.math.BigDecimal;

public interface ProductProjection {

    Long getIdProduct();

    String getName();

    String getReference();

    String getBarcode();

    Long getCategoryId();

    String getCategoryName();

    Short getTypeUnitId();

    String getTypeUnitName();

    String getTypeUnitAbbreviation();

    BigDecimal getTypeUnitBaseValue();

    Long getBranchId();

    String getBranchName();

    Long getCompanyId();

    String getCompanyName();

}
