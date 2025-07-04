package com.interfija.masterposmultitenant.utils.config;

/**
 * Enum que representa las diferentes configuraciones utilizadas en la aplicación.
 *
 * @author Steven Arzuza.
 */
public enum ConfigEnum {

    /**
     * Configuración para el color del tema.
     */
    THEME_COLOR("theme.color"),

    /**
     * Configuración para el modo táctil.
     */
    MODE_TOUCH("mode.touch"),

    /**
     * Configuración para el driver de la base de datos.
     */
    DATA_BASE_DRIVER("spring.datasource.driver-class-name"),

    /**
     * Configuración para el dialecto de hibernate para la base de datos.
     */
    DATA_BASE_DIALECT("spring.jpa.properties.hibernate.dialect"),

    /**
     * Configuración para la URL completa de la base de datos.
     */
    DATA_BASE_URL_COMPLETE("spring.datasource.url"),

    /**
     * Configuración para la URL de la base de datos.
     */
    DATA_BASE_URL("database.url"),

    /**
     * Configuración para el nombre de host de la base de datos.
     */
    DATA_BASE_HOSTNAME("database.hostname"),

    /**
     * Configuración para el nombre de usuario de la base de datos.
     */
    DATA_BASE_USERNAME("spring.datasource.username"),

    /**
     * Configuración para la contraseña de la base de datos.
     */
    DATA_BASE_PASSWORD("spring.datasource.password"),

    /**
     * Configuración para el puerto de la base de datos.
     */
    DATA_BASE_PORT("database.port"),

    /**
     * Configuración para el nombre de la base de datos.
     */
    DATA_BASE_NAME("database.name"),

    /**
     * Configuración para el complemento de la URL de la base de datos.
     */
    DATA_BASE_URL_COMPLEMENT("database.urlComplement"),

    /**
     * Configuración para verificar el certificado del servidor de la base de datos.
     */
    DATA_BASE_VERIFY_SERVER_CERTIFICATE("database.verifyServerCertificate"),

    /**
     * Configuración para la URL del almacén de claves de certificado confiables de la base de datos.
     */
    DATA_BASE_TRUST_CERTIFICATE_KEY_STORE_URL("database.trustCertificateKeyStoreUrl"),

    /**
     * Configuración para la contraseña del almacén de claves de certificado confiables de la base de datos.
     */
    DATA_BASE_TRUST_CERTIFICATE_KEY_STORE_PASSWORD("database.trustCertificateKeyStorePassword"),

    /**
     * Configuración para el nivel de registro.
     */
    LOG_LEVEL("log.level"),

    /**
     * Configuración para la ruta del archivo de registro.
     */
    LOG_FILE_PATH("log.file.path"),

    /**
     * Configuración para el tipo de visor de cliente.
     */
    TYPE_CUSTOMER_VIEWER("typeCustomerViewer"),

    /**
     * Configuración para la impresora.
     */
    PRINTER("printer."),

    /**
     * Configuración para el tipo de impresora.
     */
    TYPE_PRINTER("typePrinter."),

    /**
     * Configuración para los informes de impresora.
     */
    PRINTER_REPORTS("printerReports"),

    /**
     * Configuración para el tipo de balanza.
     */
    TYPE_WEIGHT("typeWeight"),

    /**
     * Configuración para el puerto de la balanza.
     */
    PORT_WEIGHT("portWeight"),

    /**
     * Configuración para el tipo de escáner.
     */
    TYPE_SCANNER("typeScanner"),

    /**
     * Configuración para el puerto del escáner.
     */
    PORT_SCANNER("portScanner"),

    /**
     * Configuración para el modo de vista.
     */
    MODE_VIEW("modeView"),

    /**
     * Configuración para el modo de venta.
     */
    MODE_SALE("modeSale"),

    /**
     * Configuración para la instancia única de la aplicación.
     */
    UNIQUE_INSTANCE("uniqueInstance"),

    /**
     * Configuración para el cambio de servicio.
     */
    SERVICE_CHANGE("serviceChange"),

    /**
     * Configuración para la asignación de empleados.
     */
    ASSIGN_EMPLOYEE("assignEmployee"),

    /**
     * Configuración para el grupo de productos en venta.
     */
    GROUP_PRODUCT_SALE("groupProductSale"),

    /**
     * Configuración para el modo de asignación de empleados.
     */
    MODE_ASSIGN_EMPLOYEE("modeAssignEmployee"),

    /**
     * Configuración para la fuente de la interfaz.
     */
    FONT("font"),

    /**
     * Configuración para identificar el nombre de la ubicación.
     */
    BRANCH_NAME("branchName"),

    /**
     * Configuración para identificar el nombre del terminal.
     */
    TERMINAL_NAME("terminalName"),

    /**
     * Configuración para identificar el nombre de la empresa.
     */
    COMPANY_NAME("companyName"),

    /**
     * Configuración para identificar el nombre del piso.
     */
    FLOOR_NAME("floorName");

    /**
     * Nombre de la configuración asociada a la constante.
     */
    final String configEnum;

    /**
     * Constructor privado reservado para la clase de tipo enum.
     * Se utiliza para asociar un nombre de configuración a cada constante en el enum.
     *
     * @param configEnum el nombre de la configuración.
     */
    ConfigEnum(String configEnum) {
        this.configEnum = configEnum;
    }

    /**
     * Obtiene el nombre de la configuración asociada a la constante.
     *
     * @return Nombre de la configuración como cadena de texto.
     */
    public String getConfig() {
        return configEnum;
    }

}
