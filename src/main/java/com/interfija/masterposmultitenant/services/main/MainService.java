package com.interfija.masterposmultitenant.services.main;

import com.interfija.masterposmultitenant.services.base.BaseService;

/**
 * Modelo principal de la aplicación.
 * Esta clase gestiona la lógica central de la aplicación y coordina las interacciones entre los diferentes
 * componentes de la misma.
 * <p>
 * Él {@code MainModel} actúa como un punto de acceso central a las funcionalidades principales de la aplicación,
 * permitiendo interactuar con otras clases de modelo para realizar operaciones comunes y gestionar la información.
 *
 * @author Steven Arzuza.
 */
public class MainService extends BaseService {

    /**
     * Constructor que inicializa los objetos necesarios para la gestión de principal del sistema.
     * Inicializa la fuente de datos y otros servicios.
     */
    public MainService() {
        setLogger(MainService.class);
    }

}
