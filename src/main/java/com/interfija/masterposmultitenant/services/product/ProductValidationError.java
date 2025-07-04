package com.interfija.masterposmultitenant.services.product;

/**
 * Enum que representa los posibles errores de validación que pueden ocurrir
 * al intentar guardar o actualizar un producto en el sistema.
 * <p>
 * Se utiliza para identificar de forma clara y estructurada cuál campo
 * presenta un conflicto de duplicación (nombre, referencia o código de barra).
 *
 * @author Steven Arzuza.
 */
public enum ProductValidationError {

    /**
     * No se encontró ningún error de validación.
     * Todos los campos son únicos y válidos.
     */
    NONE,

    /**
     * Ya existe un producto registrado con el mismo nombre.
     */
    NAME_EXISTS,

    /**
     * Ya existe un producto registrado con la misma referencia.
     */
    REFERENCE_EXISTS,

    /**
     * Ya existe un producto registrado con el mismo código de barra.
     */
    BARCODE_EXISTS

}

