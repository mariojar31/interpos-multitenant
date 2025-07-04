package com.interfija.masterposmultitenant.utils.resource;

/**
 * Enum que representa los diferentes paquetes utilizados en la aplicación.
 * Cada constante de este enum corresponde a una ruta de paquete utilizada para organizar
 * y acceder a recursos dentro de la aplicación.
 *
 * @author Steven Arzuza.
 */
public enum PackageEnum {

    /**
     * Paquete que contiene la configuración de la aplicación.
     */
    CONFIG(".interpos/"),

    /**
     * Paquete que contiene las imágenes de la aplicación.
     */
    IMAGES("/images"),

    /**
     * Paquete que contiene los archivos locales de la aplicación.
     */
    LOCALES("/locales");

    /**
     * Ruta del paquete asociada a la constante.
     */
    private final String packageEnum;

    /**
     * Constructor privado del enum {@link PackageEnum}, utilizado para inicializar la ruta del paquete.
     *
     * @param packageEnum Ruta del paquete como cadena de texto.
     */
    PackageEnum(String packageEnum) {
        this.packageEnum = packageEnum;
    }

    /**
     * Devuelve la ruta del paquete asociada a la constante.
     *
     * @return Ruta del paquete como cadena de texto.
     */
    public String getPackage() {
        return packageEnum;
    }
}
