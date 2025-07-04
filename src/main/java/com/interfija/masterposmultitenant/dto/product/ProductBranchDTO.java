package com.interfija.masterposmultitenant.dto.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.interfija.masterposmultitenant.dto.branch.BranchDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductBranchDTO {

    /**
     * Identificador único del stock del producto.
     */
    private Long idProductBranch;

    /**
     * Nombre del producto relacionado con este stock.
     */
    private String nameProduct;

    /**
     * Tipo de comisión asignada a la factura ($ o %).
     */
    private String commissionType;

    /**
     * Valor de la comisión asignada a la factura.
     */
    private BigDecimal valueCommission;

    /**
     * Número de impresora asociado al producto.
     */
    private String printerNumber;

    /**
     * Cantidad de unidades del producto disponibles en este stock.
     */
    private BigDecimal quantity;

    /**
     * Indica si el producto es visible en la interfaz.
     */
    private boolean visible;

    /**
     * Lista de precios asociados al producto.
     */
    private List<ProductBranchPriceDTO> pricesList;

    /**
     * Lista de lotes asociados al producto.
     */
    private List<ProductBranchBatchDTO> batchesList;

    /**
     * Identificado único de la sucursal donde se encuentra el stock del producto.
     */
    private BranchDTO branchDTO;

    /**
     * Producto al que pertenece este stock.
     */
    private ProductDTO productDTO;

    public ProductBranchDTO() {
        this.pricesList = new ArrayList<>();
        this.batchesList = new ArrayList<>();
    }

    public ProductBranchDTO(ProductBranchDTO productBranchDTO) {
        this.idProductBranch = productBranchDTO.idProductBranch;
        this.nameProduct = productBranchDTO.nameProduct;
        this.printerNumber = productBranchDTO.printerNumber;
        this.commissionType = productBranchDTO.getCommissionType();
        this.valueCommission = productBranchDTO.getValueCommission();
        this.quantity = productBranchDTO.quantity;
        this.visible = productBranchDTO.visible;
        this.branchDTO = productBranchDTO.branchDTO;
        this.productDTO = productBranchDTO.productDTO;
        this.pricesList = productBranchDTO.pricesList == null
                ? new ArrayList<>()
                : new ArrayList<>(productBranchDTO.pricesList);
        this.batchesList = productBranchDTO.batchesList == null
                ? new ArrayList<>()
                : new ArrayList<>(productBranchDTO.batchesList);
    }

    public ProductBranchDTO(Long idProductBranch) {
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
        ProductBranchDTO that = (ProductBranchDTO) obj;
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
