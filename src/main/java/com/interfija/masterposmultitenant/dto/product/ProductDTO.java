package com.interfija.masterposmultitenant.dto.product;

import com.interfija.masterposmultitenant.dto.category.CategoryDTO;
import com.interfija.masterposmultitenant.dto.other.BarcodeTypeDTO;
import com.interfija.masterposmultitenant.dto.other.TypeUnitDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representa un producto.
 *
 * @author Steven Arzuza.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
public class ProductDTO {

    /**
     * Identificador único del producto.
     */
    private Long idProduct;

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
    private String name;

    /**
     * Icono representativo del producto.
     */
    private byte[] image;

    /**
     * Indica si el producto es un servicio.
     */
    private boolean service;

    /**
     * Indica si el producto tiene un precio variable.
     */
    private boolean variablePrice;

    /**
     * Indica si el producto es combo.
     */
    private boolean bundle;

    /**
     * Tipo de código de barras asociado al producto.
     */
    private BarcodeTypeDTO barcodeTypeDTO;

    /**
     * Categoría a la que pertenece el producto.
     */
    private CategoryDTO categoryDTO;

    /**
     * Unidad de medida asociada al producto.
     */
    private TypeUnitDTO typeUnitDTO;

    /**
     * Lista de impuestos asociados al producto.
     */
    private List<ProductTaxDTO> taxesList;

    /**
     * Lista de sucursales del producto.
     */
    private List<ProductBranchDTO> branchesList;

    /**
     * Lista de productos asociados al producto representado como combo.
     */
    private List<ProductBundleDTO> productsList;

    public ProductDTO() {
        this.taxesList = new ArrayList<>();
        this.branchesList = new ArrayList<>();
        this.productsList = new ArrayList<>();
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
        ProductDTO that = (ProductDTO) obj;
        return Objects.equals(idProduct, that.idProduct);
    }

    /**
     * Devuelve el código hash de este objeto.
     *
     * @return el código hash basado en el identificador del producto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idProduct);
    }

    /**
     * Devuelve una representación en cadena del producto, que consiste en su nombre.
     *
     * @return el nombre del producto.
     */
    @Override
    public String toString() {
        return name == null ? "" : name;
    }

}
