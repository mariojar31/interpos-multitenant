package com.interfija.masterposmultitenant.utils;

/**
 * Enum que representa las diferentes acciones disponibles para realizar en el sistema.
 * Este enum se utiliza para definir las operaciones básicas que pueden ejecutarse,
 * como insertar, actualizar o eliminar, además de una opción para indicar la ausencia de acción.
 *
 * @author Steven Arzuza.
 */
public enum ActionEnum {

    /**
     * Acción de inserción de datos en el sistema.
     */
    INSERT("insertar"),

    /**
     * Acción de actualización de datos existentes en el sistema.
     */
    UPDATE("actualizar"),

    /**
     * Acción de eliminación de datos en el sistema.
     */
    DELETE("eliminar"),

    /**
     * Acción que representa la ausencia de operación.
     */
    NONE("ninguna");

    /**
     * Valor de la acción representada por el enum.
     */
    final String actionEnum;

    /**
     * Constructor privado reservado para la clase de tipo enum.
     *
     * @param actionEnum Acción representada por el enum.
     */
    ActionEnum(String actionEnum) {
        this.actionEnum = actionEnum;
    }

    /**
     * Obtiene la acción asociada a la constante.
     *
     * @return Acción del enum.
     */
    public String getAction() {
        return actionEnum;
    }

}
