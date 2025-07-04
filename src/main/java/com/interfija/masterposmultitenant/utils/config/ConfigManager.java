package com.interfija.masterposmultitenant.utils.config;

import com.interfija.masterposmultitenant.utils.PropertiesManager;
import com.interfija.masterposmultitenant.utils.resource.FileEnum;
import com.interfija.masterposmultitenant.utils.resource.PackageEnum;
import com.interfija.masterposmultitenant.utils.resource.ResourceFinder;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * Clase encargada de gestionar la configuración de la aplicación. Esta clase es un singleton que asegura
 * que solo exista una instancia de configuración en la aplicación.
 * <p>
 * Proporciona métodos para acceder a los parámetros de configuración y manejarlos desde un archivo de propiedades.
 * Utiliza la clase {@link PropertiesManager} para leer y escribir los parámetros de configuración, y usa
 * {@link Logger} para registrar los eventos y errores relacionados con la carga y manipulación de la configuración.
 *
 * @author Steven Arzuza.
 */
public class ConfigManager {

    /**
     * Instancia única de la clase {@link ConfigManager}, implementada como un singleton.
     */
    private static volatile ConfigManager instance;

    /**
     * Objeto encargado de manejar la carga y almacenamiento de las propiedades de configuración.
     */
    @Getter
    @Setter
    private PropertiesManager properties;

    /**
     * Logger para registrar eventos y errores relacionados con la configuración.
     */
    private final Logger logger;

    /**
     * Nombre del directorio donde se encuentra el archivo de configuración.
     */
    private final String nameDirectory;

    /**
     * Nombre del archivo de configuración.
     */
    private final String nameFile;

    /**
     * Objeto {@link File} que representa el archivo de configuración.
     */
    private File configFile;

    /**
     * Constructor privado para evitar la creación de instancias externas.
     * Inicializa el logger.
     */
    public ConfigManager() {
        this.logger = LoggerFactory.getLogger(ConfigManager.class);
        this.nameDirectory = PackageEnum.CONFIG.getPackage();
        this.nameFile = FileEnum.CONFIG.getFile();
    }

    /**
     * Obtiene la instancia única de la clase ConfigManager.
     *
     * @return La instancia única de ConfigManager.
     */
    public static ConfigManager getInstance() {
        if (instance == null) {
            synchronized (ConfigManager.class) {
                if (instance == null) {
                    instance = new ConfigManager();
                }
            }
        }
        return instance;
    }

    /**
     * Crea el archivo de configuración si no existe, validando la existencia del directorio y archivo.
     *
     * @throws IOException Sí ocurre un error al crear el archivo o directorio de configuración.
     */
    public void createFileConfig() throws IOException {
        logger.debug("Validando la existencia del archivo '{}'...", nameFile);
        try {
            File configDir = new File(ResourceFinder.findDirectoryUserHome() + nameDirectory.replace("/", ""));

            if (!configDir.exists()) {
                logger.info("Creando el directorio de configuracion InterPOS...");
                if (!configDir.mkdirs()) {
                    throw new IOException("No se pudo crear el directorio " + nameDirectory.replace("/", ""));
                }
            }

            if (!getConfigFile().exists()) {
                logger.info("Archivo '{}' no encontrado, creando con valores por defecto...", nameFile);
                createDefaultConfig();
                logger.info("Configuracion del software InterPOS creada correctamente.");
            }

            findConfig();
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    /**
     * Crea la configuración por defecto en el archivo de configuración.
     *
     * @throws IOException Si ocurre un error al crear el archivo de configuración.
     */
    private void createDefaultConfig() throws IOException {
        PropertiesManager properties = new PropertiesManager();

        Map<ConfigEnum, String> defaultConfig = Map.ofEntries(
                // Base de datos
                Map.entry(ConfigEnum.DATA_BASE_URL_COMPLETE, ""),
                Map.entry(ConfigEnum.DATA_BASE_DIALECT, "org.hibernate.dialect.MariaDBDialect"),
                Map.entry(ConfigEnum.DATA_BASE_DRIVER, "org.mariadb.jdbc.Driver"),
                Map.entry(ConfigEnum.DATA_BASE_URL, "jdbc:mariadb://"),
                Map.entry(ConfigEnum.DATA_BASE_HOSTNAME, "37.60.225.186"),
                Map.entry(ConfigEnum.DATA_BASE_USERNAME, ""),
                Map.entry(ConfigEnum.DATA_BASE_PASSWORD, ""),
                Map.entry(ConfigEnum.DATA_BASE_PORT, "3306"),
                Map.entry(ConfigEnum.DATA_BASE_NAME, "interpos"),
                Map.entry(ConfigEnum.DATA_BASE_URL_COMPLEMENT, "?useMode=true&requireSSL=true"),
                Map.entry(ConfigEnum.DATA_BASE_VERIFY_SERVER_CERTIFICATE, "false"),
                Map.entry(ConfigEnum.DATA_BASE_TRUST_CERTIFICATE_KEY_STORE_URL, "file:/path/to/ca-cert.pem"),
                Map.entry(ConfigEnum.DATA_BASE_TRUST_CERTIFICATE_KEY_STORE_PASSWORD, ""),

                // Logging
                Map.entry(ConfigEnum.LOG_LEVEL, "DEBUG"),
                Map.entry(ConfigEnum.LOG_FILE_PATH, "logs/app.log"),

                // Apariencia
                Map.entry(ConfigEnum.THEME_COLOR, "Claro"),
                Map.entry(ConfigEnum.FONT, "Arial"),

                // Modo de operación
                Map.entry(ConfigEnum.MODE_TOUCH, "true"),
                Map.entry(ConfigEnum.MODE_VIEW, "Ventana"),
                Map.entry(ConfigEnum.MODE_SALE, "Estandar 1 (Productos)"),

                // Comportamiento
                Map.entry(ConfigEnum.UNIQUE_INSTANCE, "true"),
                Map.entry(ConfigEnum.SERVICE_CHANGE, "false"),
                Map.entry(ConfigEnum.ASSIGN_EMPLOYEE, "false"),
                Map.entry(ConfigEnum.GROUP_PRODUCT_SALE, "true"),

                // Identificación
                Map.entry(ConfigEnum.BRANCH_NAME, "Sucursal 1"),
                Map.entry(ConfigEnum.TERMINAL_NAME, "Caja 1"),
                Map.entry(ConfigEnum.COMPANY_NAME, "InterPOS"),
                Map.entry(ConfigEnum.FLOOR_NAME, "Piso 1")
        );

        for (Map.Entry<ConfigEnum, String> entry : defaultConfig.entrySet()) {
            properties.setProperty(entry.getKey().getConfig(), entry.getValue());
        }

        saveConfig(properties);
    }

    /**
     * Busca y carga la configuración desde el archivo de configuración.
     *
     * @throws IOException Si ocurre un error al leer el archivo de configuración.
     */
    private void findConfig() throws IOException {
        try (InputStream input = new FileInputStream(getConfigFile())) {
            properties = new PropertiesManager(input);
            logger.debug("Archivo '{}' existente y configurado.", nameFile);
        } catch (IOException io) {
            throw new IOException("No se pudo leer el archivo " + nameFile + " -> " + io.getMessage());
        }
    }

    /**
     * Guarda las propiedades de configuración en el archivo de configuración.
     *
     * @param properties Las propiedades que se van a guardar en el archivo de configuración.
     * @throws IOException Si ocurre un error al guardar el archivo de configuración.
     */
    public void saveConfig(PropertiesManager properties) throws IOException {
        try (OutputStream output = new FileOutputStream(getConfigFile())) {
            properties.store(output, "Archivo de configuracion Software InterPOS");
            logger.info("Archivo {} guardado correctamente.", nameFile);
        } catch (IOException e) {
            logger.error("Error al guardar el archivo {} -> {}.", nameFile, e.getMessage());
            throw new IOException("No se pudo guardar el archivo " + nameFile);
        }
    }

    /**
     * Obtiene el archivo de configuración.
     *
     * @return El archivo de configuración.
     */
    private File getConfigFile() {
        if (configFile == null) {
            configFile = new File(getPathFile());
        }
        return configFile;
    }

    public String getPathFile() {
        return ResourceFinder.findResourceUserHomeAsString(PackageEnum.CONFIG, FileEnum.CONFIG.getFile());
    }

    public String getPathDirectoryConfig() {
        return ResourceFinder.findDirectoryUserHome().concat(PackageEnum.CONFIG.getPackage());
    }

}
