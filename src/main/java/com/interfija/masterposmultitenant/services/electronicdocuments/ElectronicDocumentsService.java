package com.interfija.masterposmultitenant.services.electronicdocuments;

import com.interfija.masterposmultitenant.services.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Clase que gestiona las operaciones relacionadas con los documentos electrónicos dentro del sistema.
 * Esta clase maneja las instancias de acceso a la base de datos para los documentos electrónicos,
 * permitiendo realizar operaciones como obtener, crear y actualizar documentos electrónicos.
 *
 * @author Steven Arzuza.
 */
@Service
public class ElectronicDocumentsService extends BaseService {

    /**
     * Constructor que inicializa los objetos necesarios para la gestión de documentos electrónicos.
     * Inicializa la fuente de datos, el DAO de categoría y los datos maestros.
     */

    public ElectronicDocumentsService() {
        setLogger(ElectronicDocumentsService.class);
    }

}
