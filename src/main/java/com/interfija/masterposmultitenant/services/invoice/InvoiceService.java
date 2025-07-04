package com.interfija.masterposmultitenant.services.invoice;

import com.interfija.masterposmultitenant.dto.invoice.InvoiceCustomerDTO;
import com.interfija.masterposmultitenant.dto.invoice.InvoiceDTO;
import com.interfija.masterposmultitenant.dto.invoice.InvoiceEmployeeDTO;
import com.interfija.masterposmultitenant.dto.invoice.InvoicePaymentDTO;
import com.interfija.masterposmultitenant.dto.invoice.InvoiceProductDTO;
import com.interfija.masterposmultitenant.dto.invoice.InvoiceProductEmployeeDTO;
import com.interfija.masterposmultitenant.dto.invoice.InvoiceProductTaxDTO;
import com.interfija.masterposmultitenant.dto.invoice.InvoiceTaxDTO;
import com.interfija.masterposmultitenant.entities.tenant.invoice.InvoiceCustomerEntity;
import com.interfija.masterposmultitenant.entities.tenant.invoice.InvoiceEmployeeEntity;
import com.interfija.masterposmultitenant.entities.tenant.invoice.InvoiceEntity;
import com.interfija.masterposmultitenant.entities.tenant.invoice.InvoicePaymentEntity;
import com.interfija.masterposmultitenant.entities.tenant.invoice.InvoiceProductEmployeeEntity;
import com.interfija.masterposmultitenant.entities.tenant.invoice.InvoiceProductEntity;
import com.interfija.masterposmultitenant.entities.tenant.invoice.InvoiceProductTaxEntity;
import com.interfija.masterposmultitenant.entities.tenant.invoice.InvoiceTaxEntity;
import com.interfija.masterposmultitenant.mappers.invoice.InvoiceCustomerMapper;
import com.interfija.masterposmultitenant.mappers.invoice.InvoiceEmployeeMapper;
import com.interfija.masterposmultitenant.mappers.invoice.InvoiceMapper;
import com.interfija.masterposmultitenant.mappers.invoice.InvoicePaymentMapper;
import com.interfija.masterposmultitenant.mappers.invoice.InvoiceProductEmployeeMapper;
import com.interfija.masterposmultitenant.mappers.invoice.InvoiceProductMapper;
import com.interfija.masterposmultitenant.mappers.invoice.InvoiceProductTaxMapper;
import com.interfija.masterposmultitenant.mappers.invoice.InvoiceTaxMapper;
import com.interfija.masterposmultitenant.repository.invoice.InvoiceRepository;
import com.interfija.masterposmultitenant.services.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Clase que gestiona las operaciones relacionadas con las facturas dentro del sistema.
 * Esta clase maneja las instancias de acceso a la base de datos para las facturas,
 * permitiendo realizar operaciones como obtener y actualizar facturas asociadas.
 *
 * @author Steven Arzuza.
 */
@Service
public class InvoiceService extends BaseService {

    private final InvoiceRepository invoiceRepository;

    private final InvoiceMapper invoiceMapper;

    private final InvoiceTaxMapper taxMapper;

    private final InvoiceCustomerMapper customerMapper;

    private final InvoiceEmployeeMapper employeeMapper;

    private final InvoicePaymentMapper paymentMapper;

    private final InvoiceProductMapper productMapper;

    private final InvoiceProductTaxMapper productTaxMapper;

    private final InvoiceProductEmployeeMapper productEmployeeMapper;

    /**
     * Constructor que inicializa el modelo de facturas con la fuente de datos, el DAO de facturas
     * y los datos maestros.
     */
    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper,
                          InvoiceTaxMapper taxMapper, InvoiceCustomerMapper customerMapper,
                          InvoiceEmployeeMapper employeeMapper, InvoicePaymentMapper paymentMapper,
                          InvoiceProductMapper productMapper, InvoiceProductTaxMapper productTaxMapper,
                          InvoiceProductEmployeeMapper productEmployeeMapper) {
        setLogger(InvoiceService.class);
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
        this.taxMapper = taxMapper;
        this.customerMapper = customerMapper;
        this.employeeMapper = employeeMapper;
        this.paymentMapper = paymentMapper;
        this.productMapper = productMapper;
        this.productTaxMapper = productTaxMapper;
        this.productEmployeeMapper = productEmployeeMapper;
    }

    /**
     * Busca y devuelve una factura opcionalmente.
     *
     * @return un {@link Optional} que contiene un {@link InvoiceDTO} si la factura está presente,
     * o un {@link Optional#empty()} si no se encuentra ninguna factura.
     */
    @Transactional(readOnly = true)
    public Optional<InvoiceDTO> getInvoiceById(long idInvoice, boolean fullMapping) {
        return getInvoice(() -> invoiceRepository.findById(idInvoice), fullMapping, "id");
    }

    private Optional<InvoiceDTO> getInvoice(Supplier<Optional<InvoiceEntity>> entitySupplier, boolean fullMapping, String source) {
        try {
            Optional<InvoiceEntity> optionalInvoiceEntity = entitySupplier.get();
            if (optionalInvoiceEntity.isEmpty()) {
                return Optional.empty();
            }

            InvoiceEntity invoiceEntity = optionalInvoiceEntity.get();
            InvoiceDTO invoiceDTO = fullMapping ? fullMappingInvoice(invoiceEntity) : invoiceMapper.toDto(invoiceEntity);
            return Optional.of(invoiceDTO);
        } catch (Exception e) {
            logger.error("No se pudo obtener la factura por {} -> {}", source, e.getMessage());
            return Optional.empty();
        }
    }

    public InvoiceDTO fullMappingInvoice(InvoiceEntity invoiceEntity) {
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(invoiceEntity);

        invoiceDTO.setEmployeesList(employeeMapper.toDtoList(invoiceEntity.getEmployees()));
        invoiceDTO.setCustomersList(customerMapper.toDtoList(invoiceEntity.getCustomers()));
        invoiceDTO.setPaymentsList(paymentMapper.toDtoList(invoiceEntity.getPayments()));

        for (InvoiceProductEntity productEntity : invoiceEntity.getProducts()) {
            InvoiceProductDTO invoiceProductDTO = productMapper.toDto(productEntity);
            invoiceProductDTO.setEmployeesList(productEmployeeMapper.toDtoList(productEntity.getEmployees()));
            invoiceProductDTO.setTaxesList(productTaxMapper.toDtoList(productEntity.getTaxes()));
            invoiceDTO.getProductsList().add(invoiceProductDTO);
        }

        return invoiceDTO;
    }

    /**
     * Válida si una factura contiene la información esencial para ser procesada.
     * Verifica que la factura tenga empleados asignados, pagos registrados y productos asociados.
     *
     * @param invoiceDTO La factura a validar.
     * @return {@code true} si la factura contiene todos los datos requeridos, de lo contrario {@code false}.
     */
    private boolean validateInvoiceData(InvoiceDTO invoiceDTO) {
        Map<String, Function<InvoiceDTO, Boolean>> validations = Map.of(
                "no tiene empleados asignados", invoiceDTO1 -> !invoiceDTO1.getEmployeesList().isEmpty(),
                "no tiene pagos asignados", invoiceDTO1 -> !invoiceDTO1.getPaymentsList().isEmpty(),
                "no tiene productos asignados", invoiceDTO1 -> !invoiceDTO1.getProductsList().isEmpty()
        );

        return validateData(invoiceDTO, InvoiceDTO::getIdInvoice, validations);
    }

    private boolean validateProductsData(List<InvoiceProductDTO> invoiceProductsList) {
        Map<String, Function<InvoiceProductDTO, Boolean>> validations = Map.of(
                "no tiene impuestos asignados", invoiceProductDTO -> !invoiceProductDTO.getTaxesList().isEmpty()
        );

        return validateDataList(invoiceProductsList, InvoiceProductDTO::getName, validations);
    }

    @Transactional(readOnly = true)
    public List<InvoiceDTO> getInvoices(long branchId, LocalDateTime startDate, LocalDateTime endDate) {
        try {
            return invoiceRepository.findInvoicesByDates(branchId, startDate, endDate)
                    .stream()
                    .map(this::fullMappingInvoice)
                    .toList();
        } catch (Exception e) {
            logger.error("No se pudo obtener la lista de facturas por rango de fecha -> {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Inserta o actualiza una factura en la base de datos.
     *
     * @param invoiceDTO el objeto {@code invoiceDTO} que representa los datos de la factura.
     * @return {@code true} si la inserción fue exitosa, {@code false} en caso contrario.
     */
    @Transactional
    public boolean saveInvoice(InvoiceDTO invoiceDTO) {
        if (invoiceDTO.getIdInvoice() == null) {
            return insertInvoice(invoiceDTO);
        } else {
            return updateInvoice(invoiceDTO);
        }
    }

    /**
     * Inserta una nueva factura en la base de datos a partir del DTO proporcionado.
     *
     * @param invoiceDTO el objeto DTO que contiene los datos de la factura a insertar
     * @return {@code true} si la factura fue insertada correctamente (es decir, si se generó un ID); {@code false} en caso de error
     */
    @Transactional
    public boolean insertInvoice(InvoiceDTO invoiceDTO) {
        try {
            InvoiceEntity invoiceEntity = invoiceMapper.toEntity(invoiceDTO);
            mapAndAddEntities(invoiceDTO.getEmployeesList(), employeeMapper::toEntity, invoiceEntity::addEmployee);
            mapAndAddEntities(invoiceDTO.getTaxesList(), taxMapper::toEntity, invoiceEntity::addTax);
            mapAndAddEntities(invoiceDTO.getRetentionsList(), taxMapper::toEntity, invoiceEntity::addTax);
            mapAndAddEntities(invoiceDTO.getPaymentsList(), paymentMapper::toEntity, invoiceEntity::addPayment);
            mapAndAddEntities(invoiceDTO.getCustomersList(), customerMapper::toEntity, invoiceEntity::addCustomer);
            insertProducts(invoiceDTO.getProductsList(), invoiceEntity::addProduct);

            invoiceRepository.save(invoiceEntity);
            return invoiceEntity.getIdInvoice() != null;
        } catch (Exception e) {
            logger.error("No se pudo insertar la factura -> {}.", e.getMessage());
            return false;
        }
    }

    private void insertProducts(List<InvoiceProductDTO> dtoList, Consumer<InvoiceProductEntity> addProduct) {
        for (InvoiceProductDTO invoiceProductDTO : dtoList) {
            InvoiceProductEntity invoiceProductEntity = productMapper.toEntity(invoiceProductDTO);
            mapAndAddEntities(invoiceProductDTO.getEmployeesList(), productEmployeeMapper::toEntity, invoiceProductEntity::addEmployee);
            mapAndAddEntities(invoiceProductDTO.getTaxesList(), productTaxMapper::toEntity, invoiceProductEntity::addTax);
            mapAndAddEntities(invoiceProductDTO.getRetentionsList(), productTaxMapper::toEntity, invoiceProductEntity::addTax);
            addProduct.accept(invoiceProductEntity);
        }
    }

    /**
     * Actualiza la información de una factura existente en la base de datos utilizando el DTO proporcionado.
     *
     * @param invoiceDTO el objeto DTO con los datos actualizados de la factura
     * @return {@code true} si la actualización fue exitosa (es decir, si el ID sigue presente); {@code false} en caso de error
     */
    @Transactional
    public boolean updateInvoice(InvoiceDTO invoiceDTO) {
        try {
            Optional<InvoiceEntity> optionalInvoiceEntity = invoiceRepository.findById(invoiceDTO.getIdInvoice());
            if (optionalInvoiceEntity.isEmpty()) {
                return false;
            }

            InvoiceEntity invoiceEntity = optionalInvoiceEntity.get();
            invoiceMapper.updateExistingEntity(invoiceEntity, invoiceDTO);

            updateChildEntities(invoiceEntity.getTaxes(), invoiceDTO.getTaxesList(), InvoiceTaxDTO::getIdInvoiceTax,
                    InvoiceTaxEntity::getIdInvoiceTax, taxMapper::updateExistingEntity, taxMapper::toEntity,
                    invoiceEntity::removeTax, invoiceEntity::addTax);

            updateChildEntities(invoiceEntity.getTaxes(), invoiceDTO.getRetentionsList(), InvoiceTaxDTO::getIdInvoiceTax,
                    InvoiceTaxEntity::getIdInvoiceTax, taxMapper::updateExistingEntity, taxMapper::toEntity,
                    invoiceEntity::removeTax, invoiceEntity::addTax);

            updateChildEntities(invoiceEntity.getEmployees(), invoiceDTO.getEmployeesList(), InvoiceEmployeeDTO::getIdInvoiceEmployee,
                    InvoiceEmployeeEntity::getIdInvoiceEmployee, employeeMapper::updateExistingEntity, employeeMapper::toEntity,
                    invoiceEntity::removeEmployee, invoiceEntity::addEmployee);

            updateChildEntities(invoiceEntity.getPayments(), invoiceDTO.getPaymentsList(), InvoicePaymentDTO::getIdInvoicePayment,
                    InvoicePaymentEntity::getIdInvoicePayment, paymentMapper::updateExistingEntity, paymentMapper::toEntity,
                    invoiceEntity::removePayment, invoiceEntity::addPayment);

            updateChildEntities(invoiceEntity.getCustomers(), invoiceDTO.getCustomersList(), InvoiceCustomerDTO::getIdInvoiceCustomer,
                    InvoiceCustomerEntity::getIdInvoiceCustomer, customerMapper::updateExistingEntity, customerMapper::toEntity,
                    invoiceEntity::removeCustomer, invoiceEntity::addCustomer);

            updateProducts(invoiceDTO.getProductsList(), invoiceEntity);

            invoiceRepository.save(invoiceEntity);
            return true;
        } catch (Exception e) {
            logger.error("No se pudo actualizar la factura -> {}.", e.getMessage());
            return false;
        }
    }

    private void updateProducts(List<InvoiceProductDTO> dtoList, InvoiceEntity invoiceEntity) {
        Map<Long, InvoiceProductDTO> dtoMap = dtoList.stream()
                .filter(dto -> dto.getIdInvoiceProduct() != null)
                .collect(Collectors.toMap(InvoiceProductDTO::getIdInvoiceProduct, dto -> dto));

        // Actualizar entidades internas de los productos existentes
        for (InvoiceProductEntity entity : invoiceEntity.getProducts()) {
            InvoiceProductDTO dto = dtoMap.get(entity.getIdInvoiceProduct());
            if (dto != null) {
                updateChildEntities(entity.getEmployees(), dto.getEmployeesList(), InvoiceProductEmployeeDTO::getIdInvoiceProductEmployee,
                        InvoiceProductEmployeeEntity::getIdInvoiceProductEmployee, productEmployeeMapper::updateExistingEntity,
                        productEmployeeMapper::toEntity, entity::removeEmployee, entity::addEmployee);

                updateChildEntities(entity.getTaxes(), dto.getTaxesList(), InvoiceProductTaxDTO::getIdInvoiceProductTax,
                        InvoiceProductTaxEntity::getIdInvoiceProductTax, productTaxMapper::updateExistingEntity,
                        productTaxMapper::toEntity, entity::removeTax, entity::addTax);

                updateChildEntities(entity.getTaxes(), dto.getRetentionsList(), InvoiceProductTaxDTO::getIdInvoiceProductTax,
                        InvoiceProductTaxEntity::getIdInvoiceProductTax, productTaxMapper::updateExistingEntity,
                        productTaxMapper::toEntity, entity::removeTax, entity::addTax);
            }
        }

        // Crear nuevas entidades de productos con sus entidades internas
        List<InvoiceProductEntity> newEntityList = new ArrayList<>();
        insertProducts(dtoList, newEntityList::add);

        // Actualizar productos de la factura
        updateChildEntities(invoiceEntity.getProducts(), newEntityList, InvoiceProductEntity::getIdInvoiceProduct,
                productMapper::updateExistingEntity, invoiceEntity::removeProduct, invoiceEntity::addProduct);
    }

    /**
     * Elimina una factura en la base de datos.
     *
     * @param invoiceDTO Objeto {@code InvoiceDTO} que representa la factura a eliminar factura.
     * @return {@code true} si la operación fue exitosa, {@code false} en caso contrario.
     */
    @Transactional
    public boolean deleteInvoice(InvoiceDTO invoiceDTO) {
        try {
            invoiceRepository.delete(invoiceMapper.toEntity(invoiceDTO));
            return true;
        } catch (Exception e) {
            logger.error("No se pudo eliminar la factura -> {}.", e.getMessage());
            return false;
        }
    }

}
