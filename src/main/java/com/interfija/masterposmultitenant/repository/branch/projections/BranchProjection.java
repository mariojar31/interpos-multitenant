package com.interfija.masterposmultitenant.repository.branch.projections;

public interface BranchProjection {

    Long getIdBranch();

    String getName();

    String getAddress();

    Long getCompanyId();

    String getCompanyName();

}
