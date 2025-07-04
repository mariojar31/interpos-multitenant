package com.interfija.masterposmultitenant.repository.sale.projections;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface SaleSummaryProjection {

    Long getIdInvoice();

    LocalDateTime getDate();

    String getEmployeeNames();

    String getEmployeeLastNames();

    String getCustomerNames();

    String getCustomerLastNames();

    BigDecimal getTotal();

}
