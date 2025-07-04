package com.interfija.masterposmultitenant.dto.electronicdocuments;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Representa una resolución de documento electrónico.
 *
 * @author Steven Arzuza.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResolutionDTO {

    /**
     * Identificador único de la resolución.
     */
    private Long idResolution;

    private LocalDate resolutionDate;

    private LocalDate startDate;

    private LocalDate endDate;

    private String resolutionNumber;

    private String prefix;

    private int starSequence;

    private int currentSequence;

    private int endSequence;

}
