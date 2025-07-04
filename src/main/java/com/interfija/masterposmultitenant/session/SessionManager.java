package com.interfija.masterposmultitenant.session;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase que gestiona la sesión de atributos en una estructura de mapa.
 * Proporciona métodos para agregar, obtener, verificar y eliminar atributos de la sesión.
 * La sesión es implementada como un mapa estático y volátil.
 *
 * @author Steven Arzuza.
 */
public final class SessionManager {

    private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);

    /**
     * Constructor privado para evitar la creación de instancias de esta clase
     */
    private SessionManager() {
    }

    /**
     * Mapa estático y volátil que almacena los atributos de la sesión.
     */
    @Getter
    private static volatile Map<String, Object> session;

    /**
     * Agrega un atributo a la sesión.
     * Si la sesión no está inicializada, se crea una nueva sesión.
     *
     * @param name  Nombre del atributo.
     * @param value Valor del atributo.
     */
    public static void setAttribute(String name, Object value) {
        logger.debug("Agregando el atributo '{}'...", name);
        if (session == null) {
            logger.info("Creando una nueva sesión...");
            session = new HashMap<>();
        }
        session.put(name, value);
        logger.debug("Atributo '{}' agregado correctamente.", name);
    }

    /**
     * Obtiene el valor de un atributo de la sesión.
     *
     * @param name Nombre del atributo.
     * @return El valor del atributo si existe, o {@code null} si no se encuentra.
     */
    public static Object getAttribute(String name) {
        logger.debug("Consultando el atributo '{}'...", name);
        try {
            if (session.containsKey(name)) {
                return session.get(name);
            }
        } catch (NullPointerException npe) {
            logger.debug("No se encontró el atributo '{}'.", name);
        }
        return null;
    }

    /**
     * Verifica si un atributo existe en la sesión.
     *
     * @param name Nombre del atributo.
     * @return {@code true} si el atributo existe, {@code false} si no.
     */
    public static boolean containsAttribute(String name) {
        return session != null && session.containsKey(name);
    }

    /**
     * Elimina un atributo de la sesión.
     *
     * @param name Nombre del atributo.
     */
    public static void removeAttribute(String name) {
        logger.debug("Removiendo el atributo '{}'...", name);
        try {
            if (containsAttribute(name)) {
                session.remove(name);
                logger.debug("Atributo removido correctamente.");
            }
        } catch (NullPointerException npe) {
            logger.debug("No se pudo remover el atributo '{}'.", name);
        }
    }

}
