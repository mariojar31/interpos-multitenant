package com.interfija.masterposmultitenant.services.customer;

import com.interfija.masterposmultitenant.dto.branch.BranchDTO;
import com.interfija.masterposmultitenant.dto.company.CompanyDTO;
import com.interfija.masterposmultitenant.dto.customer.CustomerDTO;
import com.interfija.masterposmultitenant.entities.tenant.customer.CustomerEntity;
import com.interfija.masterposmultitenant.mappers.customer.CustomerMapper;
import com.interfija.masterposmultitenant.repository.customer.CustomerRepository;
import com.interfija.masterposmultitenant.repository.customer.projections.CustomerProjection;
import com.interfija.masterposmultitenant.services.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Clase que gestiona las operaciones relacionadas con los clientes dentro del sistema.
 * Esta clase maneja las instancias de acceso a la base de datos para los clientes,
 * permitiendo realizar operaciones como obtener, crear y actualizar clientes.
 *
 * @author Steven Arzuza.
 */
@Service
public class CustomerService extends BaseService {

    /**
     * Objeto que maneja las operaciones de acceso a la base de datos para los clientes.
     */
    private final CustomerRepository customerRepository;

    /**
     * Mapper para convertir entre entidades {@link CustomerEntity} y {@link CustomerDTO} relacionados.
     */
    private final CustomerMapper customerMapper;

    /**
     * Constructor que inicializa los objetos necesarios para la gestión de clientes.
     * Inicializa la fuente de datos, el DAO de cliente y los datos maestros.
     */
    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        setLogger(CustomerService.class);
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    /**
     * Obtiene una cliente registrada en el sistema.
     *
     * @return Un objeto CustomerDTO que representan la cliente registrada.
     */
    @Transactional(readOnly = true)
    public Optional<CustomerDTO> getCustomer(Long idCustomer) {
        try {
            return customerRepository.findById(idCustomer)
                    .map(customerMapper::toDto);
        } catch (Exception e) {
            logger.error("No se pudo obtener el cliente -> {}.", e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Obtiene una lista básica de clientes visibles en el sistema.
     * <p>
     * Esta lista contiene únicamente los campos esenciales de la empresa:
     * ID y nombre.
     *
     * @param visible Indica si se deben incluir solo las clientes visibles.
     * @return Una lista de objetos {@link CustomerDTO} con los datos básicos de las clientes.
     */
    @Transactional(readOnly = true)
    public List<CustomerDTO> getCustomerBasicList(boolean visible) {
        try {
            return getCustomerBasicList(customerRepository.findAllProjectedBasicByVisible(visible));
        } catch (Exception e) {
            logger.error("No se pudo obtener la lista basica de clientes -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Convierte una lista de objetos {@link CustomerProjection} en una lista básica de {@link CustomerDTO},
     * mapeando los datos relevantes del cliente.
     *
     * @param projectionList la lista de proyecciones de clientes a convertir.
     * @return una lista de objetos {@link CustomerDTO} construidos a partir de la lista de entrada.
     */
    private List<CustomerDTO> getCustomerBasicList(List<CustomerProjection> projectionList) {
        return projectionList
                .stream()
                .map(p ->
                        CustomerDTO.builder()
                                .idCustomer(p.getIdCustomer())
                                .names(p.getNames())
                                .lastNames(p.getLastNames())
                                .build()
                )
                .toList();
    }

    /**
     * Obtiene una lista básica de clientes visibles en el sistema.
     * <p>
     * Esta lista contiene únicamente los campos esenciales del cliente:
     * ID y nombre.
     *
     * @param visible   Indica si se deben incluir solo las clientes visibles.
     * @param companyId Identificador de la empresa.
     * @return Una lista de objetos {@link CustomerDTO} con los datos básicos de las clientes.
     */
    @Transactional(readOnly = true)
    public List<CustomerDTO> getCustomersBasicList(Long companyId, boolean visible) {
        try {
            return getCustomerBasicList(customerRepository.findAllProjectedBasicByCompanyIdAndVisible(companyId, visible));
        } catch (Exception e) {
            logger.error("No se pudo obtener la lista basica de clientes por empresa -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Obtiene una lista detallada (resumen) de clientes visibles en el sistema.
     * <p>
     * Esta lista incluye información adicional además del ID y nombre,
     * como dirección, identificador de la sucursal y su nombre.
     *
     * @param visible Indica si se deben incluir solo las clientes visibles.
     * @return Una lista de objetos {@link CustomerDTO} con datos resumidos de las clientes.
     */
    @Transactional(readOnly = true)
    public List<CustomerDTO> getCustomerSummaryList(boolean visible) {
        try {
            return getCustomerSummaryList(customerRepository.findAllProjectedSummaryByVisible(visible));
        } catch (Exception e) {
            logger.error("No se pudo obtener la lista detallada de clientes -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Convierte una lista de objetos {@link CustomerProjection} en una lista detallada de {@link CustomerDTO},
     * mapeando los datos relevantes de los clientes y sus respectivas sucursales.
     *
     * @param projectionList la lista de proyecciones de los clientes a convertir.
     * @return una lista de objetos {@link CustomerDTO} construidos a partir de la lista de entrada.
     */
    private List<CustomerDTO> getCustomerSummaryList(List<CustomerProjection> projectionList) {
        return projectionList
                .stream()
                .map(p -> {
                    CompanyDTO companyDTO = CompanyDTO.builder()
                            .idCompany(p.getCompanyId())
                            .name(p.getCompanyName())
                            .build();

                    BranchDTO branchDTO = BranchDTO.builder()
                            .idBranch(p.getBranchId())
                            .name(p.getBranchName())
                            .companyDTO(companyDTO)
                            .build();

                    return CustomerDTO.builder()
                            .idCustomer(p.getIdCustomer())
                            .names(p.getNames())
                            .lastNames(p.getLastNames())
                            .identificationNumber(p.getIdentificationNumber())
                            .phone(p.getPhone())
                            .branchDTO(branchDTO)
                            .build();
                })
                .toList();
    }

    /**
     * Obtiene una lista detallada (resumen) de clientes por empresa y visibles en el sistema.
     * <p>
     * Esta lista incluye información adicional además del ID y nombre,
     * como dirección, identificador de la sucursal y su nombre.
     *
     * @param visible   Indica si se deben incluir solo las clientes visibles.
     * @param companyId Identificador de la empresa.
     * @return Una lista de objetos {@link BranchDTO} con datos resumidos de las clientes.
     */
    @Transactional(readOnly = true)
    public List<CustomerDTO> getCustomerSummaryList(Long companyId, boolean visible) {
        try {
            return getCustomerSummaryList(customerRepository.findAllProjectedSummaryByCompanyIdAndVisible(companyId, visible));
        } catch (Exception e) {
            logger.error("No se pudo obtener la lista detallada de clientes por empresa -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Inserta o actualiza un cliente en la base de datos.
     *
     * @param customerDTO un Objeto CustomerDTO que representa los datos de la cliente.
     * @return {@code true} si la operación fue exitosa, {@code false} en caso contrario.
     */
    @Transactional
    public boolean saveCustomer(CustomerDTO customerDTO) {
        if (customerDTO.getIdCustomer() == null) {
            return insertCustomer(customerDTO);
        } else {
            return updateCustomer(customerDTO);
        }
    }

    /**
     * Inserta un nuevo cliente en la base de datos a partir del DTO proporcionado.
     *
     * @param customerDTO el objeto DTO que contiene los datos del cliente a insertar
     * @return {@code true} si la cliente fue insertada correctamente (es decir, si se generó un ID); {@code false} en caso de error
     */
    @Transactional
    protected boolean insertCustomer(CustomerDTO customerDTO) {
        try {
            CustomerEntity customerEntity = customerMapper.toEntity(customerDTO);
            customerRepository.save(customerEntity);
            return customerEntity.getIdCustomer() != null;
        } catch (Exception e) {
            logger.error("No se pudo insertar el cliente -> {}.", e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza la información de un cliente existente en la base de datos utilizando el DTO proporcionado.
     *
     * @param customerDTO el objeto DTO con los datos actualizados del cliente
     * @return {@code true} si la actualización fue exitosa (es decir, si el ID sigue presente); {@code false} en caso de error
     */
    @Transactional
    protected boolean updateCustomer(CustomerDTO customerDTO) {
        try {
            CustomerEntity customerEntity = customerMapper.toEntity(customerDTO);
            customerRepository.save(customerEntity);
            return true;
        } catch (Exception e) {
            logger.error("No se pudo actualizar el cliente -> {}.", e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un cliente de la base de datos según los datos del DTO proporcionado.
     *
     * @param customerDTO el objeto DTO que representa el cliente a eliminar
     * @return {@code true} si la cliente fue eliminada correctamente; {@code false} en caso de error
     */
    @Transactional
    public boolean deleteCustomer(CustomerDTO customerDTO) {
        try {
            customerRepository.delete(customerMapper.toEntity(customerDTO));
            return true;
        } catch (Exception e) {
            logger.error("No se pudo eliminar el cliente -> {}.", e.getMessage());
            return false;
        }
    }

}
