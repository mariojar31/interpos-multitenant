package com.interfija.masterposmultitenant.dto.invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.interfija.masterposmultitenant.dto.customer.CustomerDTO;
import com.interfija.masterposmultitenant.dto.employee.EmployeeDTO;
import com.interfija.masterposmultitenant.dto.floor.TableDTO;
import com.interfija.masterposmultitenant.dto.other.PaymentFormDTO;
import com.interfija.masterposmultitenant.dto.other.TypeTaxDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import static com.interfija.masterposmultitenant.utils.StandardMethod.calculateTotalCommission;

/**
 * Representa una factura.
 *
 * @author Steven Arzuza.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceDTO {

    /**
     * Identificador único de la factura.
     */
    private Long idInvoice;

    /**
     * Fecha y hora de la factura.
     */
    private LocalDateTime date;

    /**
     * Dias de termino de la factura.
     */
    private int daysTerm;

    /**
     * Fecha de termino de la factura.
     */
    private LocalDate dateTerm;

    /**
     * Forma de pago de la factura.
     */
    private PaymentFormDTO paymentFormDTO;

    /**
     * Lista de clientes asociados a la factura.
     */
    private List<InvoiceCustomerDTO> customersList;

    /**
     * Lista de empleados de la factura.
     */
    private List<InvoiceEmployeeDTO> employeesList;

    /**
     * Lista de productos de la factura.
     */
    private List<InvoiceProductDTO> productsList;

    /**
     * Valor de cargo por servicio aplicado a la factura.
     */
    private BigDecimal valueServiceChange;

    /**
     * Tipo de descuento asignado a la factura ($ o %).
     */
    private String discountType;

    /**
     * Total de descuentos aplicados a la factura.
     */
    private BigDecimal valueDiscount;

    /**
     * Tipo de comisión asignada a la factura ($ o %).
     */
    private String commissionType;

    /**
     * Valor de la comisión asignada a la factura.
     */
    private BigDecimal valueCommission;

    /**
     * Lista de pagos asociados a la factura.
     */
    private List<InvoicePaymentDTO> paymentsList;

    /**
     * Información de la mesa asociada a la factura.
     */
    private TableDTO tableDTO;

    /**
     * Indica si la factura es por domicilio.
     */
    private boolean isDelivery;

    /**
     * Observación a la factura.
     */
    private String observation;

    /**
     * Identificador único de la caja asociada a la factura.
     */
    private Long cashId;

    /**
     * Constructor vacío, inicializa la lista de la factura.
     */
    public InvoiceDTO() {
        this.productsList = new ArrayList<>();
        this.customersList = new ArrayList<>();
        this.employeesList = new ArrayList<>();
        this.paymentsList = new ArrayList<>();
    }

    /**
     * Crea una nueva instancia de {@code InvoiceDTO} con la información de la factura proporcionada.
     *
     * @param invoiceDTO Objeto con los datos de la factura.
     */
    public InvoiceDTO(InvoiceDTO invoiceDTO) {
        this.idInvoice = invoiceDTO.idInvoice;
        this.date = invoiceDTO.date;
        this.dateTerm = invoiceDTO.dateTerm;
        this.daysTerm = invoiceDTO.daysTerm;
        this.valueServiceChange = invoiceDTO.valueServiceChange;
        this.discountType = invoiceDTO.discountType;
        this.valueDiscount = invoiceDTO.valueDiscount;
        this.isDelivery = invoiceDTO.isDelivery;
        this.cashId = invoiceDTO.cashId;
        this.tableDTO = invoiceDTO.tableDTO;
        this.observation = invoiceDTO.observation;
        this.commissionType = invoiceDTO.commissionType;
        this.valueCommission = invoiceDTO.valueCommission;
        this.paymentFormDTO = invoiceDTO.paymentFormDTO;
        this.customersList = invoiceDTO.customersList == null
                ? new ArrayList<>()
                : new ArrayList<>(invoiceDTO.customersList);
        this.employeesList = invoiceDTO.employeesList == null
                ? new ArrayList<>()
                : new ArrayList<>(invoiceDTO.employeesList);
        this.productsList = invoiceDTO.productsList == null
                ? new ArrayList<>()
                : new ArrayList<>(invoiceDTO.productsList);
        this.paymentsList = invoiceDTO.paymentsList == null
                ? new ArrayList<>()
                : new ArrayList<>(invoiceDTO.paymentsList);
    }

    /**
     * Verifica si la factura es histórica (antigua).
     * Una factura se considera antigua si su identificador (`idInvoice`) es diferente de cero.
     *
     * @return {@code true} si la factura es antigua, {@code false} en caso contrario.
     */
    @JsonIgnore
    public boolean isOldInvoice() {
        return idInvoice != null;
    }

    /**
     * Establece el empleado que realiza la factura. Si ya existe un empleado, se reemplaza por el nuevo.
     *
     * @param invoiceEmployeeDTO el objeto {@link InvoiceEmployeeDTO} que representa al empleado.
     */
    public void setEmployee(InvoiceEmployeeDTO invoiceEmployeeDTO) {
        if (invoiceEmployeeDTO == null) {
            employeesList.removeIf(employee ->
                    employee.getEmployeeDTO().getRoleDTO().getIdRole() != 6);
            return;
        }

        Optional<InvoiceEmployeeDTO> existing = getEmployee();

        if (existing.isPresent()) {
            InvoiceEmployeeDTO existingDTO = existing.get();
            existingDTO.setEmployeeDTO(invoiceEmployeeDTO.getEmployeeDTO());
        } else {
            employeesList.add(invoiceEmployeeDTO);
        }
    }

    /**
     * Obtiene el empleado que realiza la factura, si existe.
     *
     * @return un {@link Optional} que contiene el empleado si está presente, de lo contrario, {@link Optional#empty()}.
     */
    @JsonIgnore
    public Optional<InvoiceEmployeeDTO> getEmployee() {
        return employeesList.stream()
                .filter(employee ->
                        employee.getEmployeeDTO().getRoleDTO().getIdRole() != 6)
                .findFirst();
    }

    /**
     * Establece el domiciliario asociado a la factura. Si ya existe un domiciliario, se reemplaza por el nuevo.
     *
     * @param employeeDTO el objeto {@link EmployeeDTO} que representa al domiciliario.
     */
    public void setDomiciliary(EmployeeDTO employeeDTO) {
        if (employeeDTO == null) {
            employeesList.removeIf(domiciliary ->
                    domiciliary.getEmployeeDTO().getRoleDTO().getIdRole() == 6);
            return;
        }

        Optional<InvoiceEmployeeDTO> existing = getDomiciliary();

        if (existing.isPresent()) {
            existing.get().setEmployeeDTO(employeeDTO);
        } else {
            employeesList.add(new InvoiceEmployeeDTO(employeeDTO));
        }
    }

    /**
     * Obtiene el domiciliario asociado a la factura, si existe.
     *
     * @return un {@link Optional} que contiene el domiciliario si está presente, de lo contrario, {@link Optional#empty()}.
     */
    @JsonIgnore
    public Optional<InvoiceEmployeeDTO> getDomiciliary() {
        return employeesList.stream()
                .filter(domiciliary ->
                        domiciliary.getEmployeeDTO().getRoleDTO().getIdRole() == 6)
                .findFirst();
    }

    /**
     * Establece el cliente que realiza la factura. Si ya existe un cliente, se reemplaza por el nuevo.
     *
     * @param customerDTO el objeto {@link CustomerDTO} que representa al cliente.
     */
    public void setCustomer(CustomerDTO customerDTO) {
        if (customerDTO == null) {
            customersList.clear();
            return;
        }

        Optional<InvoiceCustomerDTO> existing = getCustomer();

        if (existing.isPresent()) {
            existing.get().setCustomerDTO(customerDTO);
        } else {
            customersList.add(new InvoiceCustomerDTO(customerDTO));
        }
    }

    /**
     * Obtiene el cliente asociado a la factura, si existe.
     *
     * @return un {@link Optional} que contiene el cliente si está presente, de lo contrario, {@link Optional#empty()}.
     */
    @JsonIgnore
    public Optional<InvoiceCustomerDTO> getCustomer() {
        return customersList.stream().findFirst();
    }

    /**
     * Calcula el subtotal sumando los subtotales de todos los productos vendidos.
     *
     * @return Subtotal antes de impuestos y descuentos.
     */
    @JsonIgnore
    public BigDecimal getSubtotal() {
        if (productsList == null || productsList.isEmpty()) {
            return BigDecimal.ZERO.setScale(6, RoundingMode.HALF_UP);
        }

        return productsList.stream()
                .map(InvoiceProductDTO::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(6, RoundingMode.HALF_UP);
    }

    /**
     * Calcula el descuento total aplicado al producto.
     *
     * @return Total del descuento aplicado.
     */
    @JsonIgnore
    public BigDecimal getTotalDiscount() {
        if (discountType == null || valueDiscount == null) {
            return BigDecimal.ZERO.setScale(6, RoundingMode.HALF_UP);
        }

        if ("$".equals(discountType)) {
            return valueDiscount.setScale(6, RoundingMode.HALF_UP);
        } else {
            return getSubtotal()
                    .multiply(valueDiscount.divide(BigDecimal.valueOf(100), 6, RoundingMode.HALF_UP))
                    .setScale(6, RoundingMode.HALF_UP);
        }
    }

    /**
     * Calcula el total del cargo servicio aplicado a la factura.
     *
     * @return Total del cargo servicio.
     */
    @JsonIgnore
    public BigDecimal getTotalServiceChange() {
        if (valueServiceChange == null) {
            return BigDecimal.ZERO.setScale(6, RoundingMode.HALF_UP);
        }

        BigDecimal baseAmount = getSubtotal();
        BigDecimal percentage = valueServiceChange
                .divide(BigDecimal.valueOf(100), 6, RoundingMode.HALF_UP);

        return baseAmount.multiply(percentage).setScale(6, RoundingMode.HALF_UP);
    }

    /**
     * Calcula el total de impuestos sumando los impuestos de todos los productos vendidos.
     *
     * @return Total de impuestos calculados.
     */
    @JsonIgnore
    public BigDecimal getTotalTax() {
        if (productsList == null || productsList.isEmpty()) {
            return BigDecimal.ZERO.setScale(6, RoundingMode.HALF_UP);
        }

        return productsList.stream()
                .map(InvoiceProductDTO::getTotalTaxes)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(6, RoundingMode.HALF_UP);
    }

    /**
     * Obtiene la lista de impuestos (como IVA e INC) agregados de todos los productos de la factura.
     *
     * @return una lista de {@link InvoiceTaxDTO} con el tipo de impuesto, base gravable y total del impuesto.
     */
    @JsonIgnore
    public List<InvoiceTaxDTO> getTaxesList() {
        return summarizeTaxes(InvoiceProductDTO::getTaxesList);
    }

    /**
     * Obtiene la lista de retenciones (como ReteICA, ReteIVA y ReteFuente) aplicadas a los productos de la factura.
     *
     * @return una lista de {@link InvoiceTaxDTO} con el tipo de retención, base gravable y total retenido.
     */
    @JsonIgnore
    public List<InvoiceTaxDTO> getRetentionsList() {
        return summarizeTaxes(InvoiceProductDTO::getRetentionsList);
    }

    /**
     * Calcula un resumen agrupado por tipo de impuesto o retención, sumando base gravable y valor total.
     *
     * @param taxExtractor función que extrae la lista de impuestos o retenciones desde un producto.
     * @return una lista de {@link InvoiceTaxDTO} con la información agrupada por tipo de impuesto.
     */
    private List<InvoiceTaxDTO> summarizeTaxes(Function<InvoiceProductDTO, List<InvoiceProductTaxDTO>> taxExtractor) {
        if (productsList == null || productsList.isEmpty()) {
            return Collections.emptyList();
        }

        class TaxSummary {
            BigDecimal totalTax = BigDecimal.ZERO;
            BigDecimal baseAmount = BigDecimal.ZERO;
        }

        Map<TypeTaxDTO, TaxSummary> taxMap = new HashMap<>();

        for (InvoiceProductDTO invoiceProductDTO : productsList) {
            BigDecimal base = invoiceProductDTO.getSubtotal();

            List<InvoiceProductTaxDTO> taxes = taxExtractor.apply(invoiceProductDTO);
            for (InvoiceProductTaxDTO taxDTO : taxes) {
                TypeTaxDTO type = taxDTO.getTypeTaxDTO();
                BigDecimal tax = taxDTO.getTotalTax();

                TaxSummary summary = taxMap.computeIfAbsent(type, t -> new TaxSummary());
                summary.totalTax = summary.totalTax.add(tax);
                summary.baseAmount = summary.baseAmount.add(base);
            }
        }

        return taxMap.entrySet().stream()
                .map(entry -> InvoiceTaxDTO.builder()
                        .typeTaxDTO(entry.getKey())
                        .totalTax(entry.getValue().totalTax)
                        .baseAmount(entry.getValue().baseAmount)
                        .build())
                .toList();
    }

    /**
     * Calcula el total del retentenciones sumando las retenciones de todos los productos vendidos.
     *
     * @return Total de retenciones calculados.
     */
    @JsonIgnore
    public BigDecimal getTotalRetention() {
        if (productsList == null || productsList.isEmpty()) {
            return BigDecimal.ZERO.setScale(6, RoundingMode.HALF_UP);
        }

        return productsList.stream()
                .map(InvoiceProductDTO::getTotalRetentions)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(6, RoundingMode.HALF_UP);
    }

    /**
     * Calcula el total final de la factura sumando subtotal, impuestos y restando descuentos.
     *
     * @return Total final a pagar.
     */
    @JsonIgnore
    public BigDecimal getTotal() {
        BigDecimal total = productsList.stream()
                .map(InvoiceProductDTO::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(6, RoundingMode.HALF_UP);

        return total
                .add(getTotalServiceChange())
                .setScale(6, RoundingMode.HALF_UP);
    }

    /**
     * Calcula el total de comision sobre el subtotal de la factura.
     *
     * @return Total de comisión calculado.
     */
    @JsonIgnore
    public BigDecimal getTotalCommission() {
        return calculateTotalCommission(commissionType, valueCommission, getSubtotal());
    }

    /**
     * Determina si este objeto es igual a otro.
     *
     * @param obj el objeto a comparar.
     * @return {@code true} si ambos objetos son iguales, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        InvoiceDTO that = (InvoiceDTO) obj;
        return Objects.equals(idInvoice, that.idInvoice);
    }

    /**
     * Devuelve el código hash de este objeto.
     *
     * @return el código hash basado en el identificador de la factura.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idInvoice);
    }

}
