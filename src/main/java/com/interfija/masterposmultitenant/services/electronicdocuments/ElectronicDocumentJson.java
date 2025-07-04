package com.interfija.masterposmultitenant.services.electronicdocuments;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.interfija.masterposmultitenant.dto.branch.BranchDTO;
import com.interfija.masterposmultitenant.dto.company.CompanyDTO;
import com.interfija.masterposmultitenant.dto.customer.CustomerDTO;
import com.interfija.masterposmultitenant.dto.electronicdocuments.ResolutionDTO;
import com.interfija.masterposmultitenant.dto.invoice.InvoiceCustomerDTO;
import com.interfija.masterposmultitenant.dto.invoice.InvoiceDTO;
import com.interfija.masterposmultitenant.dto.invoice.InvoiceProductDTO;
import com.interfija.masterposmultitenant.dto.invoice.InvoiceProductTaxDTO;
import com.interfija.masterposmultitenant.dto.invoice.InvoiceTaxDTO;
import com.interfija.masterposmultitenant.dto.other.TypeTaxDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static com.interfija.masterposmultitenant.utils.StandardMethod.calculateVerificationDigit;
import static com.interfija.masterposmultitenant.utils.StandardMethod.formatDate;
import static com.interfija.masterposmultitenant.utils.StandardMethod.formatTime;

public class ElectronicDocumentJson {

    protected InvoiceDTO invoiceDTO;

    /**
     * Datos de la empresa
     */
    protected CompanyDTO companyDTO;

    protected BranchDTO branchDTO;

    protected ResolutionDTO resolutionDTO;

    /**
     * Datos del cliente
     */
    protected CustomerDTO customerDTO;

    protected Short idTypeElectronicDocument;

    protected String date;

    protected String time;

    /**
     * Objeto json con lo datos de la factura
     */
    private final ObjectMapper mapper;

    private final ObjectNode json;

    public ElectronicDocumentJson(InvoiceDTO invoiceDTO, CompanyDTO companyDTO, BranchDTO branchDTO,
                                  ResolutionDTO resolutionDTO, Short idTypeElectronicDocument) {
        this.invoiceDTO = invoiceDTO;
        this.companyDTO = companyDTO;
        this.branchDTO = branchDTO;
        this.resolutionDTO = resolutionDTO;
        this.customerDTO = invoiceDTO.getCustomer().map(InvoiceCustomerDTO::getCustomerDTO).orElse(null);
        this.idTypeElectronicDocument = idTypeElectronicDocument;
        this.date = formatDate(invoiceDTO.getDate());
        this.time = formatTime(invoiceDTO.getDate());
        this.mapper = new ObjectMapper();
        this.json = mapper.createObjectNode();
    }

    public ObjectNode getJson() {
        header();
        //company();
        customer();
//        delivery();
//        deliveryParty();
        paymentForm();
        invoiceLines();

//        json.put("postal_zone_code", "111711");
//        softwareManufacturer();
//        buyerBenefit();
//        cashInformation();

        BigDecimal totalDiscount = invoiceDTO.getTotalDiscount();
        if (totalDiscount.compareTo(BigDecimal.ZERO) > 0) {
            allowanceCharges(totalDiscount, invoiceDTO.getSubtotal().add(totalDiscount));
        }

        legalMonetaryTotals();
        withHoldingTaxTotal();
        taxTotals();

        return json;
    }

    /**
     * Datos de la factura electronica
     */
    private void header() {
        json.put("number", resolutionDTO.getCurrentSequence());
        json.put("type_document_id", idTypeElectronicDocument);
        json.put("date", date);
        json.put("time", time);
        json.put("resolution_number", resolutionDTO.getResolutionNumber());
        json.put("prefix", resolutionDTO.getPrefix());
        json.put("notes", invoiceDTO.getObservation());
        json.put("disable_confirmation_text", true);
        json.put("sendmail", (idTypeElectronicDocument == 1 || idTypeElectronicDocument == 4));
        json.put("sendmailtome", (idTypeElectronicDocument == 1 || idTypeElectronicDocument == 4));
    }

    /**
     * Datos de la empresa
     */
    private void company() {
        if (branchDTO == null) {
            return;
        }
        json.put("establishment_name", branchDTO.getName());
        json.put("establishment_address", branchDTO.getAddress());
        json.put("establishment_phone", branchDTO.getPhone());

        if (branchDTO.getMunicipalityDTO() == null) {
            return;
        }
        json.put("establishment_municipality", branchDTO.getMunicipalityDTO().getIdMunicipality());
        json.put("establishment_email", branchDTO.getMunicipalityDTO().getName());

    }

    /**
     * Datos del cliente
     */
    private void customer() {
        if (customerDTO == null) {
            return;
        }

        ObjectNode customerNode = mapper.createObjectNode();

        String identificationNumber = customerDTO.getIdentificationNumber();
        if (!customerDTO.getIdCustomer().equals(1L) || idTypeElectronicDocument == 11 || idTypeElectronicDocument == 13) {
            customerNode.put("dv", calculateVerificationDigit(identificationNumber));
            customerNode.put("email", customerDTO.getMail());
        }

        customerNode.put("identification_number", identificationNumber);
        customerNode.put("name", customerDTO.toString());
        customerNode.put("merchant_registration", "0000-00");
        customerNode.put("phone", customerDTO.getPhone());
        customerNode.put("address", customerDTO.getAddress());

        Short idIdentificationType = customerDTO.getIdentificationTypeDTO() == null ? 3 : customerDTO.getIdentificationTypeDTO().getIdIdentificationType();
        customerNode.put("type_document_identification_id", idIdentificationType);

        Short idTypeOrganization = customerDTO.getTypeOrganizationDTO() == null ? 2 : customerDTO.getTypeOrganizationDTO().getIdTypeOrganization();
        customerNode.put("type_organization_id", idTypeOrganization);

        Short idTypeResponsibility = customerDTO.getTypeResponsibilityDTO() == null ? 117 : customerDTO.getTypeResponsibilityDTO().getApiResponsibilityId();
        customerNode.put("type_liability_id", idTypeResponsibility);

        Long idMunicipality = customerDTO.getMunicipalityDTO() == null ? branchDTO.getMunicipalityDTO().getIdMunicipality() : customerDTO.getMunicipalityDTO().getIdMunicipality();
        customerNode.put("municipality_id", idMunicipality);

        Short idTypeRegime = customerDTO.getTypeRegimeDTO() == null ? 2 : customerDTO.getTypeRegimeDTO().getIdTypeRegime();
        customerNode.put("type_regime_id", idTypeRegime);

        Short idTax = customerDTO.getTaxDTO() == null ? 15 : customerDTO.getTaxDTO().getApiTaxId();
        customerNode.put("tax_id", idTax);

        if (idTypeElectronicDocument == 11 || idTypeElectronicDocument == 13) {
            customerNode.put("postal_zone_code", "111711");
        }

        json.set("customer", customerNode);
    }

    /**
     * Datos de entrega al cliente
     */
    private void delivery() {
        if (customerDTO.getMunicipalityDTO() == null) {
            return;
        }

        ObjectNode deliveryNode = mapper.createObjectNode();

        Long idMunicipality = customerDTO.getMunicipalityDTO() == null ? branchDTO.getMunicipalityDTO().getIdMunicipality() : customerDTO.getMunicipalityDTO().getIdMunicipality();
        deliveryNode.put("municipality_id", idMunicipality);
        deliveryNode.put("address", customerDTO.getAddress());
        deliveryNode.put("actual_delivery_date", date);
        json.set("delivery", deliveryNode);
    }

    /**
     * Datos de la empresa encargada de la entrega
     */
    private void deliveryParty() {
        ObjectNode deliveryPartyNode = mapper.createObjectNode();
        deliveryPartyNode.put("identification_number", companyDTO.getIdentificationNumber());
        deliveryPartyNode.put("name", companyDTO.getName());
        deliveryPartyNode.put("phone", companyDTO.getPhone());
        deliveryPartyNode.put("address", companyDTO.getAddress());
        deliveryPartyNode.put("email", companyDTO.getMail());
        deliveryPartyNode.put("municipality_id", companyDTO.getMunicipalityDTO().getIdMunicipality());
        json.set("deliveryparty", deliveryPartyNode);
    }

    /**
     * Datos del tipo de pago
     */
    protected void paymentForm() {
        Short typePaymentId = invoiceDTO.getPaymentsList().size() > 1
                ? 1
                : invoiceDTO.getPaymentsList().getFirst().getPaymentDTO().getTypePaymentDTO().getApiPaymentId();

        ObjectNode paymentFormNode = mapper.createObjectNode();
        paymentFormNode.put("payment_form_id", invoiceDTO.getPaymentFormDTO().getIdPaymentForm());
        paymentFormNode.put("payment_method_id", typePaymentId);
        paymentFormNode.put("payment_due_date", invoiceDTO.getDateTerm() == null ? "" : invoiceDTO.getDateTerm().toString());
        paymentFormNode.put("duration_measure", invoiceDTO.getDaysTerm());
        json.set("payment_form", paymentFormNode);
    }

    /**
     * Datos del fabricante del software
     */
    private void softwareManufacturer() {
        ObjectNode softwareManufacturerNode = mapper.createObjectNode();
        softwareManufacturerNode.put("name", "Intefija");
        softwareManufacturerNode.put("business_name", "Interfija Soluciones");
        softwareManufacturerNode.put("software_name", "InterPOS");
        json.set("software_manufacturer", softwareManufacturerNode);
    }

    /**
     * Beneficios del comprador
     */
    private void buyerBenefit() {
        ObjectNode buyerBenefitNode = mapper.createObjectNode();
        buyerBenefitNode.put("code", customerDTO.getIdentificationNumber());
        buyerBenefitNode.put("name", customerDTO.toString());
        buyerBenefitNode.put("points", "0");
        json.set("buyer_benefits", buyerBenefitNode);
    }

    /**
     * Informacion de la caja realizadora de la factura
     */
    private void cashInformation() {
//        ObjectNode cashInformationNode = mapper.createObjectNode();
//        cashInformationNode.put("plate_number", cashType);
//        cashInformationNode.put("location", location);
//        cashInformationNode.put("cashier", invoiceDTO.getEmployee().map(InvoiceEmployeeDTO::toString).orElse(null));
//        cashInformationNode.put("cash_type", cashType);
//        cashInformationNode.put("sales_code", prefixInvoice + currentSequence);
//        cashInformationNode.put("subtotal", invoiceDTO.getTotal());
//        json.set("cash_information", cashInformationNode);
    }

    /**
     * Datos de las lineas de la factura
     */
    private void invoiceLines() {
        ArrayNode invoiceLinesArray = mapper.createArrayNode();

        for (InvoiceProductDTO invoiceProductDTO : invoiceDTO.getProductsList()) {
            BigDecimal quantity = invoiceProductDTO.getQuantity();
            BigDecimal subtotal = invoiceProductDTO.getSubtotal();

            ObjectNode invoiceLinesNode = mapper.createObjectNode();
            invoiceLinesNode.put("unit_measure_id", invoiceProductDTO.getTypeUnitDTO().getApiUnitId());
            invoiceLinesNode.put("invoiced_quantity", quantity);
            invoiceLinesNode.put("line_extension_amount", subtotal);
            invoiceLinesNode.put("free_of_charge_indicator", false);

            if (invoiceProductDTO.getDiscountType() != null) {
                BigDecimal salesPriceWithQuantity = invoiceProductDTO.getSalePrice().multiply(invoiceProductDTO.getQuantity());
                invoiceLinesNode.set("allowance_charges", allowanceCharges(invoiceProductDTO.getTotalDiscount(), salesPriceWithQuantity));
            }

            ArrayNode taxTotalsArray = mapper.createArrayNode();
            for (InvoiceProductTaxDTO invoiceProductTaxDTO : invoiceProductDTO.getTaxesList()) {
                taxTotalsArray.add(taxTotalsNode(invoiceProductTaxDTO.getTypeTaxDTO(), invoiceProductTaxDTO.getTotalTax(), subtotal));
            }

            invoiceLinesNode.set("tax_totals", taxTotalsArray);

            invoiceLinesNode.put("description", invoiceProductDTO.getName());
            invoiceLinesNode.put("notes", invoiceProductDTO.getObservation());
            invoiceLinesNode.put("code", invoiceProductDTO.getBarcode());
            invoiceLinesNode.put("type_item_identification_id", 4);
            invoiceLinesNode.put("price_amount", invoiceProductDTO.getTotal());
            invoiceLinesNode.put("base_quantity", quantity);
            if (idTypeElectronicDocument == 11) {
                invoiceLinesNode.put("type_generation_transmition_id", 1);
                invoiceLinesNode.put("start_date", date);
            }
            invoiceLinesArray.add(invoiceLinesNode);
        }

        json.set("invoice_lines", invoiceLinesArray);
    }

    /**
     * Datos de los impuestos a la factura
     *
     * @return retorna un objeto de tipo JsonArray con los datos de los impuestos
     */
    private ObjectNode taxTotalsNode(TypeTaxDTO typeTaxDTO, BigDecimal taxAmount, BigDecimal taxableAmount) {
        ObjectNode taxTotalsNode = mapper.createObjectNode();
        taxTotalsNode.put("tax_id", typeTaxDTO.getTaxDTO().getApiTaxId());
        taxTotalsNode.put("tax_amount", taxAmount.setScale(1, RoundingMode.HALF_UP));
        taxTotalsNode.put("percent", typeTaxDTO.getRate());
        taxTotalsNode.put("taxable_amount", taxableAmount);
        return taxTotalsNode;
    }

    /**
     * Datos del descuento a toda la factura
     */
    private ArrayNode allowanceCharges(BigDecimal amount, BigDecimal baseAmount) {
        ArrayNode allowanceChargesArray = mapper.createArrayNode();
        ObjectNode allowanceChargesNode = mapper.createObjectNode();
        allowanceChargesNode.put("discount_id", 10);
        allowanceChargesNode.put("charge_indicator", false);
        allowanceChargesNode.put("allowance_charge_reason", "Descuento");
        allowanceChargesNode.put("amount", amount);
        allowanceChargesNode.put("base_amount", baseAmount);
        allowanceChargesArray.add(allowanceChargesNode);
        return allowanceChargesArray;
    }

    private void withHoldingTaxTotal() {
        json.set("with_holding_tax_total", taxTotalsArray(invoiceDTO.getRetentionsList()));
    }

    private ArrayNode taxTotalsArray(List<InvoiceTaxDTO> taxesList) {
        ArrayNode taxTotalsArray = mapper.createArrayNode();
        for (InvoiceTaxDTO invoiceTaxDTO : taxesList) {
            taxTotalsArray.add(taxTotalsNode(invoiceTaxDTO.getTypeTaxDTO(), invoiceTaxDTO.getTotalTax(), invoiceTaxDTO.getBaseAmount()));
        }
        return taxTotalsArray;
    }

    private void taxTotals() {
        json.set("tax_totals", taxTotalsArray(invoiceDTO.getTaxesList()));
    }

    /**
     * Datos totales de la factura
     */
    private void legalMonetaryTotals() {
        ObjectNode legalMonetaryTotalsNode = mapper.createObjectNode();
        BigDecimal subTotal = invoiceDTO.getSubtotal();
        legalMonetaryTotalsNode.put("line_extension_amount", subTotal);
        legalMonetaryTotalsNode.put("tax_exclusive_amount", subTotal);
        legalMonetaryTotalsNode.put("tax_inclusive_amount", subTotal.add(invoiceDTO.getTotalTax()));
        legalMonetaryTotalsNode.put("allowance_total_amount", invoiceDTO.getTotalDiscount());
        legalMonetaryTotalsNode.put("payable_amount", invoiceDTO.getTotal().subtract(invoiceDTO.getTotalServiceChange()));
        json.set("legal_monetary_totals", legalMonetaryTotalsNode);
    }

}
