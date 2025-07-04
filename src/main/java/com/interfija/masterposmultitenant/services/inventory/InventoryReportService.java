package com.interfija.masterposmultitenant.services.inventory;

import com.interfija.masterposmultitenant.dto.other.TypeTaxDTO;
import com.interfija.masterposmultitenant.dto.other.TypeUnitDTO;
import com.interfija.masterposmultitenant.dto.inventory.InventorySummaryDTO;
import com.interfija.masterposmultitenant.dto.product.ProductTaxDTO;
import com.interfija.masterposmultitenant.mappers.product.ProductTaxMapper;
import com.interfija.masterposmultitenant.repository.inventory.projections.InventorySummaryProjection;
import com.interfija.masterposmultitenant.repository.product.ProductRepository;
import com.interfija.masterposmultitenant.repository.product.ProductTaxRepository;
import com.interfija.masterposmultitenant.services.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static com.interfija.masterposmultitenant.utils.StandardMethod.calculatePriceWithTax;
import static com.interfija.masterposmultitenant.utils.StandardMethod.calculateTaxProduct;

/**
 * Clase que gestiona las operaciones relacionadas con los reportes de inventario dentro del sistema.
 * Esta clase maneja las instancias de acceso a la base de datos para los reportes de inventario.
 *
 * @author Steven Arzuza.
 */
@Service
public class InventoryReportService extends BaseService {

    private final ProductRepository productRepository;

    private final ProductTaxRepository productTaxRepository;

    private final ProductTaxMapper productTaxMapper;

    @Autowired
    public InventoryReportService(ProductRepository productRepository, ProductTaxRepository productTaxRepository,
                                  ProductTaxMapper productTaxMapper) {
        setLogger(InventoryReportService.class);
        this.productRepository = productRepository;
        this.productTaxRepository = productTaxRepository;
        this.productTaxMapper = productTaxMapper;
    }

    @Transactional(readOnly = true)
    public List<InventorySummaryDTO> getInventoryProduct(Long branchId) {
        try {
            List<InventorySummaryProjection> productList = productRepository.findAllByBranchIdAndDefaultPrice(branchId);

            return productList.stream().map(p -> {
                List<TypeTaxDTO> taxList = productTaxRepository.findAllByProductId(p.getIdProduct()).stream()
                        .map(productTaxMapper::toDto)
                        .map(ProductTaxDTO::getTypeTaxDTO)
                        .toList();

                TypeUnitDTO typeUnit = TypeUnitDTO.builder()
                        .idTypeUnit( p.getTypeUnitId())
                        .name(p.getTypeUnitName())
                        .abbreviation(p.getTypeUnitAbbreviation())
                        .baseValue(p.getTypeUnitBaseValue())
                        .build();

                BigDecimal quantity = p.getQuantity();
                BigDecimal salePrice = p.getSalePrice();
                BigDecimal purchasePrice = p.getPurchasePrice();

                BigDecimal priceFinal = calculatePriceWithTax(salePrice, taxList);
                BigDecimal totalPurchase = purchasePrice.multiply(quantity);
                BigDecimal totalSale = salePrice.multiply(quantity);
                BigDecimal tax = calculateTaxProduct(salePrice, taxList);
                BigDecimal totalSaleFinal = calculatePriceWithTax(totalSale, taxList);

                return InventorySummaryDTO.builder()
                        .reference(p.getReference())
                        .barcode(p.getBarcode())
                        .product(p.getName())
                        .category(p.getCategoryName())
                        .typeUnitDTO(typeUnit)
                        .quantity(quantity)
                        .salePrice(salePrice)
                        .purchasePrice(purchasePrice)
                        .tax(tax)
                        .priceFinal(priceFinal)
                        .branch(p.getBranchName())
                        .company(p.getCompanyName())
                        .taxesList(taxList)
                        .totalPurchase(totalPurchase)
                        .totalSale(totalSale)
                        .totalSaleFinal(totalSaleFinal)
                        .build();
            }).toList();
        } catch (Exception e) {
            logger.error("No se pudo obtener el inventario de productos -> {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

}
