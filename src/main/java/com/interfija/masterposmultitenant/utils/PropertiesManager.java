package com.interfija.masterposmultitenant.utils;

import com.interfija.masterposmultitenant.utils.resource.PackageEnum;
import com.interfija.masterposmultitenant.utils.resource.ResourceFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * Clase que gestiona las propiedades de configuración de la aplicación.
 * Extiende la clase {@link Properties} y añade funcionalidad para cargar,
 * almacenar y gestionar propiedades personalizadas, así como para manejar
 * los registros de log.
 *
 * @author Steven Arzuza.
 */
public class PropertiesManager extends Properties {

    /**
     * Logger utilizado para registrar eventos y errores en la gestión de propiedades.
     */
    private Logger logger;

    /**
     * Constructor vació
     */
    public PropertiesManager() {
        init();
    }

    /**
     * Crea un objeto de la clase properties con el properties requerido.
     *
     * @param inputStream archivo en bytes.
     * @throws IOException si no se pudo leer el properties.
     */
    public PropertiesManager(InputStream inputStream) throws IOException {
        loadProperties(inputStream);
        closeInputStream(inputStream);
        init();
    }

    /**
     * Inicializa el logger para la clase PropertiesManager.
     */
    private void init() {
        this.logger = LoggerFactory.getLogger(PropertiesManager.class);
    }

    /**
     * Crea un objeto de la clase properties con el properties requerido.
     *
     * @param packageEnum enum con el nombre del paquete.
     * @param file     nombre del properties.
     * @throws IOException si no se pudo leer el properties.
     */
    public PropertiesManager(PackageEnum packageEnum, String file) throws IOException {
        InputStream inputStream = ResourceFinder.findResourceAsInputStream(packageEnum, file);
        loadProperties(inputStream);
        closeInputStream(inputStream);
    }

    /**
     * Carga las propiedades desde el flujo de entrada especificado.
     *
     * @param inputStream El flujo de entrada desde el cual se cargan las propiedades.
     * @throws IOException Si ocurre un error al cargar las propiedades.
     */
    private void loadProperties(InputStream inputStream) throws IOException {
        load(inputStream);
    }

    /**
     * Cierra el flujo de entrada especificado.
     *
     * @param inputStream El flujo de entrada que se desea cerrar.
     * @throws IOException Si ocurre un error al cerrar el flujo de entrada.
     */
    private void closeInputStream(InputStream inputStream) throws IOException {
        inputStream.close();
    }

    /**
     * Establece las propiedades basándonos en el mapa de propiedades proporcionado.
     *
     * @param mapProperty Un mapa que contiene las propiedades a establecer.
     */
    public void setMapProperty(Map<String, String> mapProperty) {
        mapProperty.forEach((s, s2) -> {
            if (s != null && s2 != null) {
                setProperty(s, s2);
            }
        });
    }

    /**
     * Obtiene una propiedad booleana asociada a la clave proporcionada.
     *
     * @param key La clave de la propiedad que se desea obtener.
     * @return El valor booleano de la propiedad.
     */
    public boolean getPropertyBoolean(String key) {
        return Boolean.parseBoolean(getProperty(key));
    }

    /**
     * Obtiene una propiedad entera asociada a la clave proporcionada.
     *
     * @param key La clave de la propiedad que se desea obtener.
     * @return El valor entero de la propiedad.
     * @throws NumberFormatException Si el valor de la propiedad no puede ser convertido a un entero.
     */
    public int getPropertyInt(String key) throws NumberFormatException {
        try {
            return Integer.parseInt(getProperty(key));
        } catch (NumberFormatException e) {
            showMessage(key);
            return -1;
        }
    }

    /**
     * Obtiene una propiedad de tipo String asociada a la clave proporcionada.
     *
     * @param key La clave de la propiedad que se desea obtener.
     * @return El valor de la propiedad como String.
     */
    public String getPropertyString(String key) {
        try {
            String value = getProperty(key);
            if (value == null || value.isEmpty()) {
                throw new NullPointerException();
            }
            return value;
        } catch (NullPointerException ignored) {
            showMessage(key);
            return "";
        }
    }

    /**
     * Muestra un mensaje de advertencia cuando una propiedad no está configurada.
     *
     * @param key La clave de la propiedad que no está configurada.
     */
    private void showMessage(String key) {
        logger.warn("Propiedad '{}' no configurada.", key);
    }

}
