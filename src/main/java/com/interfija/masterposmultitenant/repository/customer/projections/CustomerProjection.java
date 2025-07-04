package com.interfija.masterposmultitenant.repository.customer.projections;

public interface CustomerProjection {

    Long getIdCustomer();

    String getNames();

    String getLastNames();

    String getIdentificationNumber();

    String getPhone();

    Long getBranchId();

    String getBranchName();

    Long getCompanyId();

    String getCompanyName();

}
