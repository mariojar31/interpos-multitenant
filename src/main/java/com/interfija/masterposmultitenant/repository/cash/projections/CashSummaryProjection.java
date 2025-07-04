package com.interfija.masterposmultitenant.repository.cash.projections;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface CashSummaryProjection {

    Long getIdCash();

    Integer getSequence();

    LocalDateTime getStartDate();

    LocalDateTime getEndDate();

    BigDecimal getTotal();

}
