package com.interfija.masterposmultitenant.dto.other;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * Representa el tipo de código de barras asociado a un producto, incluyendo su identificador y el nombre del tipo.
 *
 * @author Steven Arzuza.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BarcodeTypeDTO {

    /**
     * Identificador único del tipo de código de barras.
     */
    private short idBarcodeType;

    /**
     * Nombre del tipo de código de barras (por ejemplo, "EAN", "UPC").
     */
    private String name;

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
        BarcodeTypeDTO that = (BarcodeTypeDTO) obj;
        return idBarcodeType == that.idBarcodeType;
    }

    /**
     * Devuelve el código hash de este objeto.
     *
     * @return el código hash basado en el identificador del tipo de código de barras.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idBarcodeType);
    }

    /**
     * Devuelve una representación en forma de cadena del tipo de código de barras.
     *
     * @return el nombre del tipo de código de barras.
     */
    @Override
    public String toString() {
        return name == null ? "" : name;
    }

}
