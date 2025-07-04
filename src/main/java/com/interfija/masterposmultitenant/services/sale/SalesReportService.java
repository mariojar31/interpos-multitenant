package com.interfija.masterposmultitenant.services.sale;

import com.interfija.masterposmultitenant.dto.invoice.InvoiceDTO;
import com.interfija.masterposmultitenant.dto.invoice.InvoicePaymentDTO;
import com.interfija.masterposmultitenant.dto.invoice.InvoiceProductDTO;
import com.interfija.masterposmultitenant.dto.other.TypeUnitDTO;
import com.interfija.masterposmultitenant.dto.sale.CommissionInvoiceManualDTO;
import com.interfija.masterposmultitenant.dto.sale.CommissionProductDTO;
import com.interfija.masterposmultitenant.dto.sale.SaleDailyDTO;
import com.interfija.masterposmultitenant.dto.sale.SaleSummaryDTO;
import com.interfija.masterposmultitenant.entities.tenant.invoice.InvoiceEntity;
import com.interfija.masterposmultitenant.repository.invoice.InvoiceProductRepository;
import com.interfija.masterposmultitenant.repository.invoice.InvoiceRepository;
import com.interfija.masterposmultitenant.repository.invoice.projections.CommissionInvoiceManualProjection;
import com.interfija.masterposmultitenant.repository.invoice.projections.CommissionProductProjection;
import com.interfija.masterposmultitenant.services.base.BaseService;
import com.interfija.masterposmultitenant.services.invoice.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.interfija.masterposmultitenant.dto.invoice.TypeTaxEnum.RETE_ICA;
import static com.interfija.masterposmultitenant.dto.invoice.TypeTaxEnum.RETE_SOURCE;
import static com.interfija.masterposmultitenant.utils.StandardMethod.calculateUtility;

/**
 * Clase que gestiona las operaciones relacionadas con los reportes de ventas dentro del sistema.
 * Esta clase maneja las instancias de acceso a la base de datos para los reportes de ventas.
 *
 * @author Steven Arzuza.
 */
@Service
public class SalesReportService extends BaseService {

    private final InvoiceRepository invoiceRepository;

    private final InvoiceService invoiceService;

    private final InvoiceProductRepository invoiceProductRepository;

    @Autowired
    public SalesReportService(InvoiceRepository invoiceRepository, InvoiceService invoiceService,
                              InvoiceProductRepository invoiceProductRepository) {
        setLogger(SalesReportService.class);
        this.invoiceRepository = invoiceRepository;
        this.invoiceService = invoiceService;
        this.invoiceProductRepository = invoiceProductRepository;
    }

    @Transactional(readOnly = true)
    public List<SaleSummaryDTO> getSalesSummaries(long branchId, LocalDateTime startDate, LocalDateTime endDate) {
        return invoiceRepository.findSaleSummaries(branchId, startDate, endDate).stream()
                .map(p -> SaleSummaryDTO.builder()
                        .idInvoice(p.getIdInvoice())
                        .date(p.getDate())
                        .customer(concatStrings(' ', p.getCustomerNames(), p.getCustomerLastNames()))
                        .employee(concatStrings(' ', p.getEmployeeNames(), p.getEmployeeLastNames()))
                        .total(p.getTotal())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SaleDailyDTO> getSalesDaily(long branchId, LocalDateTime startDate, LocalDateTime endDate) {
        List<InvoiceEntity> invoiceEntityList = invoiceRepository.findInvoicesByDates(branchId, startDate, endDate);
        if (invoiceEntityList.isEmpty()) {
            return Collections.emptyList();
        }

        List<SaleDailyDTO> saleDailyList = new ArrayList<>();
        for (InvoiceEntity invoiceEntity : invoiceEntityList) {
            InvoiceDTO invoiceDTO = invoiceService.fullMappingInvoice(invoiceEntity);

            String terminal = invoiceEntity.getCashEntity().getTerminalEntity().getName();
            String customer = invoiceDTO.getCustomer().map(Object::toString).orElse("");
            String employee = invoiceDTO.getEmployee().map(Object::toString).orElse("");
            String commissionType = invoiceDTO.getCommissionType();
            BigDecimal valueCommission = invoiceDTO.getValueCommission();
            String payments = getConcatPayments(invoiceDTO.getPaymentsList());
            LocalDateTime date = invoiceDTO.getDate();

            for (InvoiceProductDTO product : invoiceDTO.getProductsList()) {
                SaleDailyDTO saleDailyDTO = buildSaleDailyDTO(product, date, terminal, customer, employee,
                        commissionType, valueCommission, payments);

                saleDailyList.add(saleDailyDTO);
            }
        }

        return saleDailyList;
    }

    private String getConcatPayments(List<InvoicePaymentDTO> paymentList) {
        return paymentList
                .stream()
                .map(invoicePaymentDTO ->
                        invoicePaymentDTO.getPaymentDTO().getTypePaymentDTO().toString())
                .collect(Collectors.joining(" "));
    }

    private SaleDailyDTO buildSaleDailyDTO(InvoiceProductDTO product, LocalDateTime date, String terminal, String customer,
                                           String employee, String commissionType, BigDecimal commissionValue, String payments) {

        BigDecimal reteSourceRate = product.getRetentionByCode(RETE_SOURCE)
                .map(dto -> dto.getTypeTaxDTO().getRate())
                .orElse(null);

        BigDecimal reteIcaRate = product.getRetentionByCode(RETE_ICA)
                .map(dto -> dto.getTypeTaxDTO().getRate())
                .orElse(null);

        BigDecimal utility = calculateUtility(product.getPurchasePrice(), product.getSalePrice(), product.getDiscountType(),
                product.getValueDiscount(), reteSourceRate, reteIcaRate, product.getQuantity(), commissionType, commissionValue);

        return SaleDailyDTO.builder()
                .date(date)
                .terminal(terminal)
                .barcode(product.getBarcode())
                .category(product.getCategoryDTO().toString())
                .product(product.getName())
                .typeUnitDTO(product.getTypeUnitDTO())
                .quantity(product.getQuantity())
                .purchasePrice(product.getPurchasePrice())
                .salePrice(product.getSalePrice())
                .priceFinal(product.getFinalPrice())
                .customer(customer)
                .employee(employee)
                .commissionType(commissionType)
                .commissionValue(commissionValue)
                .discountType(product.getDiscountType())
                .discountValue(product.getValueDiscount())
                .retention(product.getTotalIndividualRetentions())
                .payments(payments)
                .totalTax(product.getTotalTaxes())
                .totalSale(product.getSubtotal())
                .totalSaleFinal(product.getTotal())
                .totalRetention(product.getTotalRetentions())
                .totalUtility(utility)
                .build();
    }

    @Transactional(readOnly = true)
    public List<CommissionProductDTO> getCommissionProductSummaries(long branchId, LocalDateTime startDate, LocalDateTime endDate) {
        try {
            List<CommissionProductProjection> commisionProductList = invoiceProductRepository.findAllCommissionSummaryByBranchId(
                    branchId, startDate, endDate);

            return commisionProductList.stream().map(p -> {
                TypeUnitDTO typeUnit = TypeUnitDTO.builder()
                        .idTypeUnit( p.getTypeUnitId())
                        .name(p.getTypeUnitName())
                        .abbreviation(p.getTypeUnitAbbreviation())
                        .baseValue(p.getTypeUnitBaseValue())
                        .build();

                String customer = concatStrings(' ', p.getCustomerNames(), p.getCustomerLastNames());
                String employee = concatStrings(' ', p.getEmployeeNames(), p.getEmployeeLastNames());

                return CommissionProductDTO.builder()
                        .date(p.getDate())
                        .barcode(p.getBarcode())
                        .product(p.getProductName())
                        .salePrice(p.getSalePrice())
                        .quantity(p.getQuantity())
                        .typeUnitDTO(typeUnit)
                        .commissionType(p.getCommissionType())
                        .commissionValue(p.getValueCommission())
                        .branch(p.getBranchName())
                        .company(p.getCompanyName())
                        .customer(customer)
                        .employee(employee)
                        .totalCommission(p.getTotalCommission())
                        .build();
            }).toList();
        } catch (Exception e) {
            logger.error("No se pudo obtener la comisión de los productos -> {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    @Transactional(readOnly = true)
    public List<CommissionInvoiceManualDTO> getCommissionInvoiceManualSummaries(long branchId, LocalDateTime startDate, LocalDateTime endDate) {
        try {
            List<CommissionInvoiceManualProjection> commisionInvoiceManualList = invoiceRepository.findAllCommissionInvoiceManualSummaryByBranchId(
                    branchId, startDate, endDate);

            return commisionInvoiceManualList.stream().map(p -> {
                String customer = concatStrings(' ', p.getCustomerNames(), p.getCustomerLastNames());
                String employee = concatStrings(' ', p.getEmployeeNames(), p.getEmployeeLastNames());

                return CommissionInvoiceManualDTO.builder()
                        .date(p.getDate())
                        .numberInvoice("")
                        .commissionType(p.getCommissionType())
                        .commissionValue(p.getValueCommission())
                        .branch(p.getBranchName())
                        .company(p.getCompanyName())
                        .customer(customer)
                        .customerPhone(p.getCustomerPhone())
                        .employee(employee)
                        .subtotal(p.getSubtotal())
                        .total(p.getTotal())
                        .totalCommission(p.getTotalCommission())
                        .build();
            }).toList();
        } catch (Exception e) {
            logger.error("No se pudo obtener la comisión manual de la factura -> {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

}
