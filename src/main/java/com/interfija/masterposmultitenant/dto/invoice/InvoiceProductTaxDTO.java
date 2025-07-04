package com.interfija.masterposmultitenant.dto.invoice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.interfija.masterposmultitenant.dto.other.TypeTaxDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Representa el impuesto de una linea de la factura.
 *
 * @author Steven Arzuza.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceProductTaxDTO {

    /**
     * Identificador único del impuesto del producto de la factura
     */
    private Long idInvoiceProductTax;

    /**
     * Impuesto asociado al producto de la factura.
     */
    private TypeTaxDTO typeTaxDTO;

    private BigDecimal totalTax;

    /**
     * Identificador único del producto de la factura
     */
    private Long invoiceProductId;

    public InvoiceProductTaxDTO(TypeTaxDTO typeTaxDTO) {
        this.typeTaxDTO = typeTaxDTO;
    }

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
        InvoiceProductTaxDTO that = (InvoiceProductTaxDTO) obj;
        return Objects.equals(idInvoiceProductTax, that.idInvoiceProductTax);
    }

    /**
     * Devuelve el código hash de este objeto.
     *
     * @return el código hash basado en el identificador del impuesto del producto de la factura.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idInvoiceProductTax);
    }

    /**
     * Devuelve una representación en forma de cadena del nombre del impuesto.
     *
     * @return el nombre del impuesto.
     */
    @Override
    public String toString() {
        if (typeTaxDTO != null) {
            return typeTaxDTO.toString();
        }
        return "";
    }

}
