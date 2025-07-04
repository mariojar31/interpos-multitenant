package com.interfija.masterposmultitenant.repository.employee.projections;

public interface EmployeeProjection {

    Long getIdEmployee();

    String getNames();

    String getLastNames();

    String getIdentificationNumber();

    String getPhone();

    Short getRoleId();

    String getRoleName();

    Long getBranchId();

    String getBranchName();

    Long getCompanyId();

    String getCompanyName();

}
