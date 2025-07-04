package com.interfija.masterposmultitenant.dto.product;

import com.interfija.masterposmultitenant.dto.supplier.SupplierDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Representa la información de precios asociados a un producto.
 *
 * @author Steven Arzuza.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductBranchPriceDTO {

    /**
     * Identificador único del precio del producto.
     */
    private Long idProductBranchPrice;

    /**
     * Identificador del tipo de precio (por ejemplo, precio regular, promocional, etc.).
     */
    private int priceTypeId;

    /**
     * Identificador de la condición del precio (por ejemplo, descuento por volumen, oferta especial, etc.).
     */
    private int priceConditionId;

    /**
     * Cantidad mínima requerida para aplicar este precio.
     */
    private int minimumQuantity;

    /**
     * Precio de compra del producto.
     */
    private BigDecimal purchasePrice;

    /**
     * Precio de venta del producto.
     */
    private BigDecimal salePrice;

    /**
     * Fecha de inicio de la vigencia de este precio.
     */
    private LocalDateTime startDate;

    /**
     * Fecha de finalización de la vigencia de este precio.
     */
    private LocalDateTime endDate;

    /**
     * Precio de venta principal del producto.
     */
    private boolean defaultPrice;

    /**
     * Proveedor asociado al precio.
     */
    private SupplierDTO supplierDTO;

    /**
     * Producto al que pertenece este precio.
     */
    private ProductBranchDTO productBranchDTO;

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
        ProductBranchPriceDTO that = (ProductBranchPriceDTO) obj;
        return Objects.equals(idProductBranchPrice, that.idProductBranchPrice);
    }

    /**
     * Devuelve el código hash de este objeto.
     *
     * @return el código hash basado en el identificador del precio del producto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idProductBranchPrice);
    }

}
