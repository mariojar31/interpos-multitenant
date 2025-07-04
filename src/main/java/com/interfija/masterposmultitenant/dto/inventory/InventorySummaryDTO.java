package com.interfija.masterposmultitenant.dto.inventory;

import com.interfija.masterposmultitenant.dto.other.TypeTaxDTO;
import com.interfija.masterposmultitenant.dto.other.TypeUnitDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Representa un producto del inventario.
 *
 * @author Steven Arzuza.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
public class InventorySummaryDTO {

    /**
     * Referencia del producto.
     */
    private String reference;

    /**
     * Código de barras del producto.
     */
    private String barcode;

    /**
     * Nombre del producto.
     */
    private String product;

    /**
     * Categoría a la que pertenece el producto.
     */
    private String category;

    /**
     * Unidad de medida asociada al producto.
     */
    private TypeUnitDTO typeUnitDTO;

    /**
     * Lista de impuestos asociados al producto.
     */
    private List<TypeTaxDTO> taxesList;

    /**
     * Sucursal donde se encuentra el stock del producto.
     */
    private String branch;

    /**
     * Empresa donde se encuentra el stock del producto.
     */
    private String company;

    /**
     * Precio de compra del producto.
     */
    private BigDecimal purchasePrice;

    /**
     * Precio de venta del producto.
     */
    private BigDecimal salePrice;

    private BigDecimal tax;

    private BigDecimal priceFinal;

    /**
     * Cantidad de unidades del producto disponibles en este stock.
     */
    private BigDecimal quantity;

    private BigDecimal totalPurchase;

    private BigDecimal totalSale;

    private BigDecimal totalSaleFinal;

}
