package com.interfija.masterposmultitenant.dto.invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.interfija.masterposmultitenant.dto.category.CategoryDTO;
import com.interfija.masterposmultitenant.dto.other.TypeTaxDTO;
import com.interfija.masterposmultitenant.dto.product.ProductDTO;
import com.interfija.masterposmultitenant.dto.product.ProductBranchPriceDTO;
import com.interfija.masterposmultitenant.dto.product.ProductBranchDTO;
import com.interfija.masterposmultitenant.dto.product.ProductTaxDTO;
import com.interfija.masterposmultitenant.dto.other.TypeUnitDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import static com.interfija.masterposmultitenant.dto.invoice.TypeTaxEnum.INC;
import static com.interfija.masterposmultitenant.dto.invoice.TypeTaxEnum.IVA;
import static com.interfija.masterposmultitenant.dto.invoice.TypeTaxEnum.RETE_ICA;
import static com.interfija.masterposmultitenant.dto.invoice.TypeTaxEnum.RETE_IVA;
import static com.interfija.masterposmultitenant.dto.invoice.TypeTaxEnum.RETE_SOURCE;
import static com.interfija.masterposmultitenant.utils.StandardMethod.calculateTaxInvoiceLine;
import static com.interfija.masterposmultitenant.utils.StandardMethod.calculateTotalCommission;

/**
 * Representa una linea de la factura.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceProductDTO {

    /**
     * Identificador único del producto de la factura
     */
    private Long idInvoiceProduct;

    /**
     * Referencia del producto en la venta.
     */
    private String reference;

    /**
     * Código de barras del producto.
     */
    private String barcode;

    /**
     * Nombre del producto en la venta.
     */
    private String name;

    /**
     * Precio de venta del producto.
     */
    private BigDecimal salePrice;

    /**
     * Precio de compra del producto.
     */
    private BigDecimal purchasePrice;

    /**
     * Categoría a la que pertenece el producto.
     */
    private CategoryDTO categoryDTO;

    /**
     * Lista de impuestos asociados al producto.
     */
    private List<InvoiceProductTaxDTO> taxesList;

    /**
     * Unidad de medida del producto.
     */
    private TypeUnitDTO typeUnitDTO;

    /**
     * Existencia del producto.
     */
    private ProductBranchDTO productBranchDTO;

    /**
     * Indica si el producto es un servicio.
     */
    private boolean service;

    /**
     * Indica si el precio del producto es variable.
     */
    private boolean variablePrice;

    /**
     * Indica si el producto es combo.
     */
    private boolean bundle;

    /**
     * Número de impresora asociado al producto.
     */
    private String printerNumber;

    /**
     * Cantidad de productos vendidos.
     */
    private BigDecimal quantity;

    /**
     * Cantidad de productos vendidos para realizar cálculo de stock si la factura es antigua (histórica).
     */
    private BigDecimal oldQuantity;

    /**
     * Tipo de descuento asignado al producto ($ o %).
     */
    private String discountType;

    /**
     * Descuento aplicado al producto (Porcentaje o Valor Fijo).
     */
    private BigDecimal valueDiscount;

    /**
     * Tipo de comisión asignada al producto ($ o %).
     */
    private String commissionType;

    /**
     * Valor de la comisión asignada al producto.
     */
    private BigDecimal valueCommission;

    /**
     * Lista de empleados que se le asignara la comisión del producto.
     */
    private List<InvoiceProductEmployeeDTO> employeesList;

    /**
     * Observación al producto.
     */
    private String observation;

    /**
     * Identificador único asociado a la factura
     */
    private Long invoiceId;

    /**
     * Constructor vacío, inicializa la lista del producto.
     */
    public InvoiceProductDTO() {
        this.taxesList = new ArrayList<>();
        this.employeesList = new ArrayList<>();
        this.taxesList = new ArrayList<>();
    }

    public InvoiceProductDTO(InvoiceProductDTO invoiceProductDTO) {
        this.idInvoiceProduct = invoiceProductDTO.idInvoiceProduct;
        this.reference = invoiceProductDTO.reference;
        this.barcode = invoiceProductDTO.barcode;
        this.name = invoiceProductDTO.name;
        this.salePrice = invoiceProductDTO.salePrice;
        this.purchasePrice = invoiceProductDTO.purchasePrice;
        this.categoryDTO = invoiceProductDTO.categoryDTO != null ? new CategoryDTO(invoiceProductDTO.categoryDTO) : null;
        this.typeUnitDTO = invoiceProductDTO.typeUnitDTO;
        this.productBranchDTO = invoiceProductDTO.productBranchDTO != null ? new ProductBranchDTO(invoiceProductDTO.productBranchDTO) : null;
        this.service = invoiceProductDTO.service;
        this.variablePrice = invoiceProductDTO.variablePrice;
        this.bundle = invoiceProductDTO.bundle;
        this.printerNumber = invoiceProductDTO.printerNumber;
        this.quantity = invoiceProductDTO.quantity;
        this.oldQuantity = invoiceProductDTO.oldQuantity;
        this.discountType = invoiceProductDTO.discountType;
        this.valueDiscount = invoiceProductDTO.valueDiscount;
        this.commissionType = invoiceProductDTO.commissionType;
        this.valueCommission = invoiceProductDTO.valueCommission;
        this.observation = invoiceProductDTO.observation;
        this.invoiceId = invoiceProductDTO.invoiceId;
        this.employeesList = invoiceProductDTO.employeesList == null
                ? new ArrayList<>()
                : new ArrayList<>(invoiceProductDTO.employeesList);
        this.taxesList = invoiceProductDTO.taxesList == null
                ? new ArrayList<>()
                : new ArrayList<>(invoiceProductDTO.taxesList);
    }

    /**
     * Constructor que crea un {@code InvoiceLineDto} a partir de un {@code ProductDto}.
     * Inicializa las propiedades del producto de venta basándose en los valores del producto.
     *
     * @param productDTO el producto base para crear el producto de venta.
     */
    public InvoiceProductDTO(ProductDTO productDTO, ProductBranchPriceDTO productBranchPriceDto) {
        ProductBranchDTO productBranchDTO = productDTO.getBranchesList().getFirst();

        this.reference = productDTO.getReference();
        this.barcode = productDTO.getBarcode();
        this.name = productDTO.getName();
        this.salePrice = productBranchPriceDto.getSalePrice();
        this.purchasePrice = productBranchPriceDto.getPurchasePrice();
        this.categoryDTO = productDTO.getCategoryDTO();
        this.taxesList = !productDTO.isBundle() ? setInvoiceLineTax(productDTO.getTaxesList()) : new ArrayList<>();
        this.typeUnitDTO = productDTO.getTypeUnitDTO();
        this.service = productDTO.isService();
        this.variablePrice = productDTO.isVariablePrice();
        this.printerNumber = productBranchDTO.getPrinterNumber();
        this.quantity = BigDecimal.ONE;
        this.productBranchDTO = productBranchDTO;
        this.commissionType = productBranchDTO.getCommissionType();
        this.valueCommission = productBranchDTO.getValueCommission();
        this.employeesList = new ArrayList<>();
    }

    /**
     * Convierte una lista de {@code ProductTaxDto} en una lista de {@code InvoiceLineTaxDto}.
     *
     * @param taxesList Lista de impuestos asociados a un producto.
     * @return Lista de objetos {@code InvoiceLineTaxDto} con los impuestos correspondientes.
     */
    private List<InvoiceProductTaxDTO> setInvoiceLineTax(List<ProductTaxDTO> taxesList) {
        if (taxesList == null || taxesList.isEmpty()) {
            return Collections.emptyList();
        }

        return new ArrayList<>(taxesList.stream()
                .map(productTaxDto -> InvoiceProductTaxDTO.builder()
                        .typeTaxDTO(productTaxDto.getTypeTaxDTO())
                        .totalTax(BigDecimal.ZERO)
                        .build())
                .toList());
    }

    /**
     * Obtiene y calcula los impuestos (IVA e INC) aplicables a este producto.
     *
     * @return una lista de {@link InvoiceProductTaxDTO} con el impuesto aplicado y el total calculado.
     */
    @JsonIgnore
    public List<InvoiceProductTaxDTO> getTaxesList() {
        return computeTaxes(code -> IVA.getTax().equals(code) || INC.getTax().equals(code));
    }

    /**
     * Obtiene y calcula las retenciones (ReteICA, ReteIVA y ReteFuente) aplicables a este producto.
     *
     * @return una lista de {@link InvoiceProductTaxDTO} con la retención aplicada y el total calculado.
     */
    @JsonIgnore
    public List<InvoiceProductTaxDTO> getRetentionsList() {
        return computeTaxes(code -> RETE_ICA.getTax().equals(code)
                || RETE_IVA.getTax().equals(code)
                || RETE_SOURCE.getTax().equals(code));
    }

    /**
     * Calcula el monto total de impuestos o retenciones aplicados a este producto,
     * filtrando por los códigos proporcionados y basándose en la tarifa y el subtotal.
     *
     * @param taxFilter un predicado que indica qué impuestos o retenciones deben incluirse.
     * @return una lista de {@link InvoiceProductTaxDTO} con los totales calculados por cada impuesto.
     */
    private List<InvoiceProductTaxDTO> computeTaxes(Predicate<String> taxFilter) {
        BigDecimal subtotal = getSubtotal();

        List<InvoiceProductTaxDTO> filteredTaxes = this.taxesList.stream()
                .filter(taxDTO -> taxFilter.test(taxDTO.getTypeTaxDTO().getTaxDTO().getCode()))
                .toList();

        for (InvoiceProductTaxDTO taxDTO : filteredTaxes) {
            BigDecimal rate = taxDTO.getTypeTaxDTO().getRate();
            BigDecimal taxAmount = BigDecimal.ZERO;

            if (rate != null) {
                taxAmount = subtotal.multiply(rate.divide(BigDecimal.valueOf(100), 6, RoundingMode.HALF_UP))
                        .setScale(6, RoundingMode.HALF_UP);
            }

            taxDTO.setTotalTax(taxAmount);
        }

        return filteredTaxes;
    }

    /**
     * Establece el empleado que se le asignara la comisión del producto. Si ya existe un empleado, se reemplaza por el nuevo.
     *
     * @param productEmployeeDTO el objeto {@link InvoiceProductEmployeeDTO} que representa al empleado.
     */
    public void setEmployee(InvoiceProductEmployeeDTO productEmployeeDTO) {
        if (productEmployeeDTO == null) {
            employeesList.removeIf(employee ->
                    employee.getEmployeeDTO().getRoleDTO().getIdRole() != 6);
            return;
        }

        Optional<InvoiceProductEmployeeDTO> existing = getEmployee();

        if (existing.isPresent()) {
            InvoiceProductEmployeeDTO existingDTO = existing.get();
            existingDTO.setEmployeeDTO(productEmployeeDTO.getEmployeeDTO());
        } else {
            employeesList.add(productEmployeeDTO);
        }
    }

    /**
     * Obtiene el empleado que se le asignó la comisión del producto, si existe.
     *
     * @return un {@link Optional} que contiene el empleado si está presente, de lo contrario, {@link Optional#empty()}.
     */
    @JsonIgnore
    public Optional<InvoiceProductEmployeeDTO> getEmployee() {
        return employeesList.stream()
                .filter(employee ->
                        employee.getEmployeeDTO().getRoleDTO().getIdRole() != 6)
                .findFirst();
    }

    /**
     * Calcula el subtotal del producto sin descuentos ni impuestos.
     *
     * @return Subtotal antes de aplicar impuestos y descuentos.
     */
    @JsonIgnore
    public BigDecimal getSubtotal() {
        return salePrice
                .multiply(quantity)
                .subtract(getTotalDiscount())
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

        BigDecimal discount = getTotalIndividualDiscount();

        if ("$".equals(discountType)) {
            return discount;
        } else {
            return discount
                    .multiply(quantity)
                    .setScale(6, RoundingMode.HALF_UP);
        }
    }

    @JsonIgnore
    public BigDecimal getTotalIndividualDiscount() {
        if (discountType == null || valueDiscount == null) {
            return BigDecimal.ZERO.setScale(6, RoundingMode.HALF_UP);
        }

        if ("$".equals(discountType)) {
            return valueDiscount.setScale(6, RoundingMode.HALF_UP);
        } else {
            return salePrice
                    .multiply(valueDiscount.divide(BigDecimal.valueOf(100), 6, RoundingMode.HALF_UP))
                    .setScale(6, RoundingMode.HALF_UP);
        }
    }

    @JsonIgnore
    public BigDecimal getSalePriceWithTax() {
        BigDecimal newSalePrice = salePrice
                .subtract(getTotalIndividualDiscount())
                .setScale(6, RoundingMode.HALF_UP);

        return newSalePrice.add(getTotalIndividualTaxes());
    }

    @JsonIgnore
    public BigDecimal getTotalIndividualTaxes() {
        return getIndividualTaxByCode(IVA).add(getIndividualTaxByCode(INC));
    }

    @JsonIgnore
    public BigDecimal getIndividualTaxByCode(TypeTaxEnum typeTaxEnum) {
        BigDecimal newSalePrice = salePrice.subtract(getTotalIndividualDiscount());
        List<InvoiceProductTaxDTO> tax = taxesList.stream().filter(invoiceTaxDTO ->
                        invoiceTaxDTO.getTypeTaxDTO().getTaxDTO().getCode().equals(typeTaxEnum.getTax()))
                .toList();
        return calculateTaxInvoiceLine(newSalePrice, tax);
    }

    /**
     * Calcula los impuestos totales aplicados al producto.
     *
     * @return Total de impuestos calculados.
     */
    @JsonIgnore
    public BigDecimal getTotalTaxes() {
        return getTotalTaxByCode(IVA).add(getTotalTaxByCode(INC));
    }

    /**
     * Calcula el impuesto por código aplicado al producto.
     *
     * @return Total de inc calculado.
     */
    @JsonIgnore
    public BigDecimal getTotalTaxByCode(TypeTaxEnum typeTaxEnum) {
        BigDecimal subtotal = getSubtotal();
        List<InvoiceProductTaxDTO> productTaxes = taxesList.stream().filter(invoiceTaxDTO ->
                        typeTaxEnum.getTax().equals(invoiceTaxDTO.getTypeTaxDTO().getTaxDTO().getCode()))
                .toList();
        return calculateTaxInvoiceLine(subtotal, productTaxes);
    }

    /**
     * Calcula el total final del producto después de aplicar descuentos e impuestos.
     *
     * @return Total a pagar por el producto.
     */
    @JsonIgnore
    public BigDecimal getTotal() {
        return getSubtotal()
                .add(getTotalTaxes())
                .setScale(6, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotalRetentions() {
        return getTotalRetentionByCode(RETE_SOURCE)
                .add(getTotalRetentionByCode(RETE_IVA))
                .add(getTotalRetentionByCode(RETE_ICA));
    }

    @JsonIgnore
    public BigDecimal getTotalRetentionByCode(TypeTaxEnum typeTaxEnum) {
        Optional<InvoiceProductTaxDTO> optionalInvoiceProductTaxDTO = getRetentionByCode(typeTaxEnum);
        if (optionalInvoiceProductTaxDTO.isEmpty()) {
            return BigDecimal.ZERO.setScale(6, RoundingMode.HALF_UP);
        }

        BigDecimal rate = optionalInvoiceProductTaxDTO.get().getTypeTaxDTO().getRate();
        BigDecimal divisor;

        switch (typeTaxEnum) {
            case RETE_IVA -> {
                BigDecimal totalIva = getTotalTaxByCode(IVA);
                return totalIva.multiply(optionalInvoiceProductTaxDTO.get().getTypeTaxDTO().getRate()
                        .divide(BigDecimal.valueOf(100), 6, RoundingMode.HALF_UP));
            }
            case RETE_ICA -> divisor = BigDecimal.valueOf(1000);
            default -> divisor = BigDecimal.valueOf(100);
        }

        BigDecimal subtotal = getSubtotal();
        return subtotal.multiply(rate.divide(divisor, 6, RoundingMode.HALF_UP));
    }

    /**
     * Obtiene la retención de industria y comercio que se le asignó al producto, si existe.
     *
     * @return un {@link Optional} que contiene la retención si está presente, de lo contrario, {@link Optional#empty()}.
     */
    @JsonIgnore
    public Optional<InvoiceProductTaxDTO> getRetentionByCode(TypeTaxEnum typeTaxEnum) {
        return taxesList.stream()
                .filter(invoiceProductTaxDTO ->
                        typeTaxEnum.getTax().equals(invoiceProductTaxDTO.getTypeTaxDTO().getTaxDTO().getCode()))
                .findFirst();
    }

    @JsonIgnore
    public BigDecimal getFinalPrice() {
        return salePrice
                .subtract(getTotalIndividualDiscount())
                .subtract(getTotalIndividualRetentions())
                .add(getTotalIndividualTaxes())
                .setScale(6, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotalIndividualRetentions() {
        return getIndividualRetentionByCode(RETE_SOURCE)
                .add(getIndividualRetentionByCode(RETE_IVA))
                .add(getIndividualRetentionByCode(RETE_ICA));
    }

    @JsonIgnore
    public BigDecimal getIndividualRetentionByCode(TypeTaxEnum typeTaxEnum) {
        return taxesList.stream()
                .filter(tax -> tax.getTypeTaxDTO().getTaxDTO().getCode().equals(typeTaxEnum.getTax()))
                .map(tax -> {
                    BigDecimal rate = tax.getTypeTaxDTO().getRate();
                    BigDecimal divisor;

                    switch (typeTaxEnum) {
                        case RETE_IVA -> {
                            BigDecimal totalIva = getIndividualTaxByCode(IVA);
                            return totalIva.multiply(rate.divide(BigDecimal.valueOf(100), 6, RoundingMode.HALF_UP));
                        }
                        case RETE_ICA -> divisor = BigDecimal.valueOf(1000);
                        default -> divisor = BigDecimal.valueOf(100);
                    }

                    BigDecimal base = salePrice.subtract(getTotalIndividualDiscount());
                    return base.multiply(rate.divide(divisor, 6, RoundingMode.HALF_UP));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(6, RoundingMode.HALF_UP);
    }

    /**
     * Establece la retención por medio del código que se le asignara a la factura.
     * Si ya existe la retención, se reemplaza por el nuevo.
     *
     * @param typeTaxDTO el objeto {@link TypeTaxDTO} que representa la retención a la fuente.
     */
    public void setRetentionByCode(TypeTaxEnum typeTaxEnum, TypeTaxDTO typeTaxDTO) {
        if (typeTaxDTO == null) {
            taxesList.removeIf(tax -> typeTaxEnum.getTax().equals(tax.getTypeTaxDTO().getTaxDTO().getCode()));
            return;
        }

        Optional<InvoiceProductTaxDTO> existing = taxesList.stream()
                .filter(tax -> typeTaxEnum.getTax().equals(tax.getTypeTaxDTO().getTaxDTO().getCode()))
                .findFirst();

        if (existing.isPresent()) {
            existing.get().setTypeTaxDTO(typeTaxDTO);
        } else {
            taxesList.add(new InvoiceProductTaxDTO(typeTaxDTO));
        }
    }

    public void removeRetentionByCode(TypeTaxEnum typeTaxEnum) {
        taxesList.removeIf(tax -> tax.getTypeTaxDTO().getTaxDTO().getCode().equals(typeTaxEnum.getTax()));
    }

    /**
     * Calcula el total de comision del producto.
     *
     * @return Total de comisión calculado.
     */
    @JsonIgnore
    public BigDecimal getTotalCommission() {
        return calculateTotalCommission(commissionType, valueCommission, salePrice.subtract(getTotalIndividualDiscount()), quantity);
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
        InvoiceProductDTO that = (InvoiceProductDTO) obj;
        return Objects.equals(idInvoiceProduct, that.idInvoiceProduct);
    }

    /**
     * Devuelve el código hash de este objeto.
     *
     * @return el código hash basado en el identificador del producto de la factura.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idInvoiceProduct);
    }

}
