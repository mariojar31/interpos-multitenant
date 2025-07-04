package com.interfija.masterposmultitenant.utils.resource;

/**
 * Enum que representa los archivos asociados necesarios para la aplicación.
 * Este enum contiene las constantes correspondientes a los archivos que se utilizan en el sistema,
 * como el archivo de configuración.
 *
 * @author Steven Arzuza.
 */
public enum FileEnum {

    /**
     * Archivo con la configuración del sistema.
     */
    CONFIG("config.properties");

    /**
     * Nombre del archivo asociado a la constante.
     */
    final String fileEnum;

    /**
     * Constructor privado reservado para la clase de tipo enum.
     * Este constructor inicializa el nombre del archivo asociado a la constante.
     *
     * @param fileEnum Nombre del archivo como cadena de texto.
     */
    FileEnum(String fileEnum) {
        this.fileEnum = fileEnum;
    }

    /**
     * Obtiene el nombre del archivo asociado a la constante.
     *
     * @return Nombre del archivo como cadena de texto.
     */
    public String getFile() {
        return fileEnum;
    }

}
