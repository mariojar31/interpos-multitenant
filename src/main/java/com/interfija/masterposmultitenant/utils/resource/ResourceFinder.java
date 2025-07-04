package com.interfija.masterposmultitenant.utils.resource;

import java.io.InputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

/**
 * Clase que se encarga de buscar los recursos necesarios para el programa.
 * Esta clase proporciona métodos para localizar archivos y recursos dentro del proyecto
 * y en el sistema de archivos del usuario, utilizando rutas predeterminadas y configuradas.
 *
 * @author Steven Arzuza.
 */
public class ResourceFinder {

    /**
     * Ruta predeterminada dentro del proyecto donde se encuentran los recursos.
     */
    private static final String PATH_DEFAULT = "/com/interfija/masterposmultitenant/";

    /**
     * Ruta del directorio home del usuario, obtenida del sistema operativo.
     */
    private static final String HOME_DIRECTORY = System.getProperty("user.home");

    /**
     * Separador de directorios específico del sistema operativo (por ejemplo, "/" en UNIX o "\" en Windows).
     */
    private static final String SEPARATOR_OS = File.separator;

    /**
     * Busca la ruta del directorio del usuario en el sistema operativo
     *
     * @return ruta del directorio del usuario
     */
    public static String findDirectoryUserHome() {
        return HOME_DIRECTORY.concat(SEPARATOR_OS);
    }

    /**
     * Busca un recurso en el sistema y lo retorna como InputStream.
     *
     * @param packageEnum enum con el nombre del paquete.
     * @param file   nombre del recurso.
     * @return el recurso como InputStream.
     */
    public static InputStream findResourceUserHomeAsInputStream(PackageEnum packageEnum, String file)
            throws FileNotFoundException {

        return new FileInputStream(HOME_DIRECTORY.concat(SEPARATOR_OS).concat(packageEnum.getPackage()).concat(file));
    }

    /**
     * Busca un recurso en el sistema y lo retorna como String.
     *
     * @param packageEnum enum con el nombre del paquete.
     * @param file   nombre del recurso.
     * @return el recurso como String.
     */
    public static String findResourceUserHomeAsString(PackageEnum packageEnum, String file) {
        return HOME_DIRECTORY.concat(SEPARATOR_OS).concat(packageEnum.getPackage()).concat(file);
    }

    /**
     * Busca un recurso en el sistema y lo retorna como InputStream.
     *
     * @param packageEnum enum con el nombre del paquete.
     * @param file   nombre del recurso.
     * @return el recurso como InputStream.
     */
    public static InputStream findResourceAsInputStream(PackageEnum packageEnum, String file) throws IOException {
        return findResourceAsUrl(packageEnum, file).openStream();
    }

    /**
     * Busca un recurso en el sistema y lo retorna como String.
     *
     * @param packageEnum enum con el nombre del paquete.
     * @param file   nombre del recurso.
     * @return el recurso como String.
     */
    public static String findResourceAsString(PackageEnum packageEnum, String file) throws NullPointerException {
        return findResourceAsUrl(packageEnum, file).toString();
    }

    /**
     * Busca un recurso del sistema.
     *
     * @param packageEnum enum con el nombre del paquete.
     * @param file   nombre del recurso.
     * @return el archivo como URL.
     */
    public static URL findResourceAsUrl(PackageEnum packageEnum, String file) throws NullPointerException {
        String resource = packageEnum.getPackage().concat("/").concat(file);
        URL url = ResourceFinder.class.getResource(resource);

        if (url == null) {
            throw new NullPointerException(getMessageFileNotFound(resource));
        }

        return url;
    }

    /**
     * Obtiene el mensaje de error para el recurso buscado.
     *
     * @param resource ruta relativa del recurso.
     * @return el mensaje de error con la ruta relativa donde se buscó el recurso.
     */
    private static String getMessageFileNotFound(String resource) {
        return "Recurso no encontrado -> {}".replace("{}", resource);
    }

}
