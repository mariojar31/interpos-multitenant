package com.interfija.masterposmultitenant.dto.branch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * Representa la configuración de una sucursal.
 *
 * @author Steven Arzuza.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BranchConfigurationDTO {

    /**
     * Identificador único de la configuración.
     */
    private Long idBranchConfiguration;

    /**
     * Prefijo de la factura.
     */
    private String prefixInvoice;

    /**
     * Encabezado de la factura.
     */
    private String headerInvoice;

    /**
     * Pie de la factura.
     */
    private String footerInvoice;

    /**
     * Código de anulación.
     */
    private String codeOverride;

    /**
     * Valor del cargo servicio.
     */
    private int valueServiceChange;

    /**
     * Sucursal al que pertenece la configuración.
     */
    private BranchDTO branchDTO;

    /**
     * Determina si este objeto es igual a otro.
     *
     * @param obj el objeto a comparar.
     * @return {@code true} si ambos objetos son iguales, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BranchConfigurationDTO that = (BranchConfigurationDTO) obj;
        return Objects.equals(idBranchConfiguration, that.idBranchConfiguration);
    }

    /**
     * Devuelve el código hash de este objeto.
     *
     * @return el código hash basado en el identificador de la configuración de la sucursal.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idBranchConfiguration);
    }

}
