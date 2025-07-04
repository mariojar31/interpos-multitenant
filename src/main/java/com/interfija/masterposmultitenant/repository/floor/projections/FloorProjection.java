package com.interfija.masterposmultitenant.repository.floor.projections;

public interface FloorProjection {

    Long getIdFloor();

    String getName();

    Long getBranchId();

    String getBranchName();

    Long getCompanyId();

    String getCompanyName();

}
