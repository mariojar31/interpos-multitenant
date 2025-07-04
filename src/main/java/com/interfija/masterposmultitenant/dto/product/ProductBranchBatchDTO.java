package com.interfija.masterposmultitenant.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Representa un lote de productos.
 *
 * @author Steven Arzuza.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductBranchBatchDTO {

    /**
     * Identificador único del lote de productos.
     */
    private Long idProductBranchBatch;

    /**
     * Código del lote.
     */
    private String batchCode;

    /**
     * Número de serie del lote.
     */
    private String serialNumber;

    /**
     * Cantidad de productos en el lote.
     */
    private BigDecimal quantity;

    /**
     * Fecha en la que se hizo el lote.
     */
    private LocalDate expeditionDate;

    /**
     * Fecha de vencimiento del lote.
     */
    private LocalDate expirationDate;

    /**
     * Fecha de ingreso del lote.
     */
    private LocalDate entryDate;

    /**
     * Producto asociado al lote.
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
        ProductBranchBatchDTO that = (ProductBranchBatchDTO) obj;
        return Objects.equals(idProductBranchBatch, that.idProductBranchBatch);
    }

    /**
     * Devuelve el código hash de este objeto.
     *
     * @return el código hash basado en el identificador del lote de productos.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idProductBranchBatch);
    }

}
