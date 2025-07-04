package com.interfija.masterposmultitenant.repository.category.projections;

public interface CategoryProjection {

    Long getIdCategory();

    String getCode();

    String getName();

    Long getParentId();

    String getParentName();

    Long getBranchId();

    String getBranchName();

    Long getCompanyId();

    String getCompanyName();

    long getProductCount();

    long getChildrenCount();

}
