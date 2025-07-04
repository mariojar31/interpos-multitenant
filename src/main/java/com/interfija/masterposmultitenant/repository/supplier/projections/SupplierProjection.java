package com.interfija.masterposmultitenant.repository.supplier.projections;

public interface SupplierProjection {

    Long getIdSupplier();

    String getNames();

    String getLastNames();

    String getIdentificationNumber();

    String getPhone();

    Long getBranchId();

    String getBranchName();

    Long getCompanyId();

    String getCompanyName();

    long getProductCount();

}
