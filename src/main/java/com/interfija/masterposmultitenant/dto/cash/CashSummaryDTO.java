package com.interfija.masterposmultitenant.dto.cash;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representa la información resumida de una caja.
 *
 * @author Steven Arzuza.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CashSummaryDTO {

    private Long idCash;

    private int sequence;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private BigDecimal total;

}
