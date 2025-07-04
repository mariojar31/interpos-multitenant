package com.interfija.masterposmultitenant.dto.invoice;

/**
 * Enum que representa los diferentes impuestos y retenciones disponibles para realizar en el sistema.
 *
 * @author Steven Arzuza.
 */
public enum TypeTaxEnum {

    /**
     * Retención a la fuente en el sistema.
     */
    IVA("01"),

    /**
     * Retención a la fuente en el sistema.
     */
    INC("04"),

    /**
     * Retención a la fuente en el sistema.
     */
    RETE_SOURCE("06"),

    /**
     * Retención del impuesto a la venta en el sistema.
     */
    RETE_IVA("05"),

    /**
     * Retención de industria y comercio en el sistema.
     */
    RETE_ICA("07");

    /**
     * Valor del impuesto representado por el enum.
     */
    final String taxEnum;

    /**
     * Constructor privado reservado para la clase de tipo enum.
     *
     * @param taxEnum Impuesto representada por el enum.
     */
    TypeTaxEnum(String taxEnum) {
        this.taxEnum = taxEnum;
    }

    /**
     * Obtiene el impuesto asociado a la constante.
     *
     * @return Impuesto del enum.
     */
    public String getTax() {
        return taxEnum;
    }

}
