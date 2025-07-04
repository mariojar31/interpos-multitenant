package com.interfija.masterposmultitenant.dto.product;

import com.interfija.masterposmultitenant.utils.ActionEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * Representa un producto de un combo.
 *
 * @author Steven Arzuza.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductBundleDTO {

    /**
     * Identificador único del producto asociado al combo.
     */
    private long idProductBundle;

    /**
     * Cantidad de productos a descontar.
     */
    private double quantity;

    /**
     * Identificador del combo del producto.
     */
    private long bundleId;

    /**
     * Producto asociado al combo.
     */
    private ProductDTO productDTO;

    /**
     * Acción asociada a este producto (por ejemplo, "crear", "modificar", "eliminar").
     */
    private ActionEnum action;

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
        ProductBundleDTO that = (ProductBundleDTO) obj;
        return idProductBundle == that.idProductBundle;
    }

    /**
     * Devuelve el código hash de este objeto.
     *
     * @return el código hash basado en el identificador del producto del combo.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idProductBundle);
    }

}
