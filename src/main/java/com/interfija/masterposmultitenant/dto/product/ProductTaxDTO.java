package com.interfija.masterposmultitenant.dto.product;

import com.interfija.masterposmultitenant.dto.other.TypeTaxDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * Representa la información de un impuesto asociado a un producto.
 *
 * @author Steven Arzuza.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductTaxDTO {

    /**
     * Identificador único del impuesto asociado al producto.
     */
    private Long idProductTax;

    /**
     * Identificador del producto asociado a este impuesto.
     */
    private Long productId;

    /**
     * Objeto que representa el impuesto asociado a este producto.
     */
    private TypeTaxDTO typeTaxDTO;

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
        ProductTaxDTO that = (ProductTaxDTO) obj;
        return Objects.equals(idProductTax, that.idProductTax);
    }

    /**
     * Devuelve el código hash de este objeto.
     *
     * @return el código hash basado en el identificador del impuesto del producto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idProductTax);
    }

}
