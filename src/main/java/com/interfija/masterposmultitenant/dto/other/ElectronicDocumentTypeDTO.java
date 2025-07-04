package com.interfija.masterposmultitenant.dto.other;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa un tipo de documento electrónico.
 *
 * @author Steven Arzuza.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ElectronicDocumentTypeDTO {

    /**
     * Identificador único del tipo de documento electrónico.
     */
    private long idElectronicDocumentType;
}
