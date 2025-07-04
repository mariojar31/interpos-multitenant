package com.interfija.masterposmultitenant.dto.product;

import com.interfija.masterposmultitenant.dto.branch.BranchDTO;
import com.interfija.masterposmultitenant.dto.other.TypeUnitDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Representa la información del stock de un producto.
 *
 * @author Steven Arzuza.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductBranchStockDTO {

    /**
     * Identificador único del stock del producto.
     */
    private Long idProductBranch;

    /**
     * Unidad de medida asociada al producto.
     */
    private TypeUnitDTO typeUnitDTO;

    /**
     * Cantidad de unidades del producto disponibles en este stock.
     */
    private BigDecimal quantity;

    /**
     * Identificado único de la sucursal donde se encuentra el stock del producto.
     */
    private BranchDTO branchDTO;

    public ProductBranchStockDTO(ProductBranchStockDTO productBranchDTO) {
        this.idProductBranch = productBranchDTO.idProductBranch;
        this.quantity = productBranchDTO.quantity;
        this.branchDTO = productBranchDTO.branchDTO;
        this.typeUnitDTO = productBranchDTO.typeUnitDTO;
    }

    public ProductBranchStockDTO(Long idProductBranch) {
        this.idProductBranch = idProductBranch;
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
        ProductBranchStockDTO that = (ProductBranchStockDTO) obj;
        return Objects.equals(idProductBranch, that.idProductBranch);
    }

    /**
     * Devuelve el código hash de este objeto.
     *
     * @return el código hash basado en el identificador del stock del producto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idProductBranch);
    }

    /**
     * Devuelve una representación en cadena de la sucursal del producto, que consiste en su nombre.
     *
     * @return el nombre de la sucursal del producto.
     */
    @Override
    public String toString() {
        if (branchDTO == null) {
            return "";
        }

        return branchDTO.toString();
    }

}
