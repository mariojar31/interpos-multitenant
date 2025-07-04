package com.interfija.masterposmultitenant.services.configuration;

import com.interfija.masterposmultitenant.services.base.BaseService;
import com.interfija.masterposmultitenant.services.company.CompanyService;
import com.interfija.masterposmultitenant.services.floor.FloorService;
import com.interfija.masterposmultitenant.services.branch.BranchService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Clase que gestiona las operaciones relacionadas con la configuración dentro del sistema.
 * Esta clase maneja las instancias de acceso a la base de datos para la configuración,
 * permitiendo realizar operaciones como obtener, crear y actualizar configuración.
 *
 * @author Steven Arzuza.
 */
@Service
public class ConfigurationService extends BaseService {

    /**
     * Objeto que maneja las operaciones de servicio a los datos para las empresas.
     */
    @Getter
    private final CompanyService companyService;

    /**
     * Objeto que maneja las operaciones de servicio a los datos para las ubicaciónes.
     */
    @Getter
    private final BranchService branchService;

    /**
     * Objeto que maneja las operaciones de servicio a los datos para los pisos.
     */
    @Getter
    private final FloorService floorService;

    public ConfigurationService() {
        this(null, null, null);
    }

    /**
     * Constructor que inicializa los objetos necesarios para la gestión de configuración del sistema.
     * Inicializa la fuente de datos, otros servicios, el DAO de configuración y los datos maestros.
     */
    @Autowired
    public ConfigurationService(CompanyService companyService, BranchService branchService, FloorService floorService) {
        setLogger(ConfigurationService.class);
        this.companyService = companyService;
        this.branchService = branchService;
        this.floorService = floorService;
    }

}
