package com.interfija.masterposmultitenant.dto.electronicdocuments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa un documento electrónico.
 *
 * @author Steven Arzuza.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ElectronicDocumentDTO {

    /**
     * Identificador único del documento electrónico.
     */
    private long idElectronicDocument;
}
