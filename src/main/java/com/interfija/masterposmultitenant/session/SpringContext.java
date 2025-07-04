package com.interfija.masterposmultitenant.session;

import com.interfija.masterposmultitenant.MasterPosMultitenantApplication;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.sql.SQLException;

/**
 * Clase utilitaria Singleton para acceder al ApplicationContext de Spring
 * desde cualquier parte de una aplicación que no esté gestionada por Spring
 * (como una aplicación Swing).
 *
 * @author Steven Arzuza.
 */
@Setter
@Getter
public class SpringContext {

    /**
     * Logger para el registro de eventos e incidencias en la interacción con el contexto de Spring Boot.
     */
    private static final Logger logger = LoggerFactory.getLogger(SpringContext.class);

    /**
     * Instancia única del Singleton.
     */
    private static SpringContext instance;

    /**
     * Contexto de aplicación de Spring, se establece desde el método main.
     */
    private ConfigurableApplicationContext applicationContext;

    /**
     * Constructor privado para evitar instanciación directa.
     */
    private SpringContext() {
    }

    /**
     * Devuelve la instancia única del Singleton. Si no existe, la crea.
     *
     * @return la instancia única de SpringContext
     */
    public static synchronized SpringContext getInstance() {
        if (instance == null) {
            instance = new SpringContext();
        }
        return instance;
    }

    /**
     * Inicia el contexto de Spring utilizando una configuración específica.
     * <p>
     * Se configura el contexto para ser no web y se especifica la ubicación adicional del archivo
     * de propiedades externo, cargado mediante {@code configManager}.
     *
     * @param args los argumentos de línea de comandos pasados a la aplicación.
     * @throws SQLException si ocurre un error relacionado con la conexión a la base de datos.
     */
    public void startContextMain(String[] args) throws SQLException {
        this.applicationContext = start(args);
    }

    public ConfigurableApplicationContext start(String[] args) throws SQLException {
        try {
            return new SpringApplicationBuilder(MasterPosMultitenantApplication.class)
                    .headless(false)
                    .web(WebApplicationType.NONE)
                    .build()
                    .run(args);
        } catch (Throwable ex) {
            Throwable rootCause = getRootCause(ex);
            logger.error("Error al iniciar el contexto Spring -> {}", rootCause.getMessage());

            if (rootCause instanceof java.sql.SQLException ||
                    rootCause instanceof IllegalArgumentException ||
                    rootCause.getMessage().contains("Failed to determine suitable jdbc url") ||
                    rootCause.getMessage().contains("Failed to determine a suitable driver class")) {

                throw new SQLException("No se pudo validar la conexión al servidor");
            } else {
                // AlertManager.error("Error inesperado al iniciar la aplicación");
                System.exit(0);
            }
        }

        return null;
    }

    /**
     * Obtiene la causa raíz de una excepción encadenada.
     *
     * @param throwable la excepción original.
     * @return la causa raíz más profunda del {@code throwable}.
     */
    private Throwable getRootCause(Throwable throwable) {
        Throwable cause = throwable;
        while (cause.getCause() != null && cause.getCause() != cause) {
            cause = cause.getCause();
        }
        return cause;
    }

    public void stop() {
        if (applicationContext != null) {
            applicationContext.close();
        }
    }

    public ConfigurableApplicationContext getContext() {
        return applicationContext;
    }

    /**
     * Devuelve un bean administrado por Spring según el tipo solicitado.
     *
     * @param <T>          el tipo del bean requerido
     * @param requiredType la clase del bean requerido
     * @return la instancia del bean administrado por Spring
     * @throws IllegalStateException si el contexto no ha sido inicializado
     */
    public <T> T getBean(Class<T> requiredType) {
        if (applicationContext == null) {
            throw new IllegalStateException("El contexto de Spring no ha sido inicializado.");
        }
        return applicationContext.getBean(requiredType);
    }

}
