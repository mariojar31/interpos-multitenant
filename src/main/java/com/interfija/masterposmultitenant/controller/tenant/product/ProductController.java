package com.interfija.masterposmultitenant.controller.tenant.product;

import com.interfija.masterposmultitenant.dto.product.ProductDTO;
import com.interfija.masterposmultitenant.services.product.ProductService;
import com.interfija.masterposmultitenant.services.product.ProductValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de productos.
 * Proporciona endpoints para operaciones CRUD y filtros complejos sobre productos.
 *
 * Autor: OpenAI para Steven Arzuza
 */
@RestController
@RequestMapping("/api/tenant/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Obtiene un producto por su ID.
     */
    @GetMapping("/{id}")
    public Optional<ProductDTO> getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    /**
     * Lista todos los productos de una sucursal, con opción de obtener mapeo completo.
     */
    @GetMapping("/branch/{branchId}")
    public List<ProductDTO> getProductByBranch(@PathVariable long branchId,
                                               @RequestParam(defaultValue = "true") boolean visible,
                                               @RequestParam(defaultValue = "false") boolean fullMapping) {
        return productService.getProductByBranchList(branchId, visible, fullMapping);
    }

    /**
     * Lista todos los productos de una empresa, con opción de mapeo completo.
     */
    @GetMapping("/company/{companyId}")
    public List<ProductDTO> getProductByCompany(@PathVariable long companyId,
                                                @RequestParam(defaultValue = "true") boolean visible,
                                                @RequestParam(defaultValue = "false") boolean fullMapping) {
        return productService.getProductByCompanyList(companyId, visible, fullMapping);
    }

    /**
     * Lista productos por empresa con filtros personalizados (expiración, inventario, mínimos).
     */
    @GetMapping("/company/{companyId}/filtered")
    public List<ProductDTO> getProductByCompanyAndFilters(@PathVariable long companyId,
                                                          @RequestParam boolean visible,
                                                          @RequestParam boolean expired,
                                                          @RequestParam boolean inventoryControl,
                                                          @RequestParam LocalDate expirationDate,
                                                          @RequestParam BigDecimal minUni,
                                                          @RequestParam BigDecimal minKg,
                                                          @RequestParam BigDecimal minL,
                                                          @RequestParam BigDecimal minM) {
        return productService.getProductByCompanyAndFiltersList(companyId, visible, expired, inventoryControl,
                expirationDate, minUni, minKg, minL, minM);
    }

    /**
     * Lista productos de una sucursal con solo precios por defecto.
     */
    @GetMapping("/branch/{branchId}/default-price")
    public List<ProductDTO> getProductsWithDefaultPrice(@PathVariable Long branchId) {
        return productService.getProductWithDefaultPrice(branchId);
    }

    /**
     * Inserta o actualiza un producto.
     */
    @PostMapping
    public boolean saveProduct(@RequestBody ProductDTO productDTO) {
        return productService.saveProduct(productDTO);
    }

    /**
     * Valida si un producto tiene nombre, referencia o código de barras duplicados.
     */
    @GetMapping("/validate")
    public ProductValidationError validateProduct(@RequestParam String name,
                                                  @RequestParam String reference,
                                                  @RequestParam String barcode,
                                                  @RequestParam(required = false) Long idProduct) {
        return productService.validateProduct(name, reference, barcode, idProduct);
    }

    /**
     * Elimina un producto.
     */
    @DeleteMapping
    public boolean deleteProduct(@RequestBody ProductDTO productDTO) {
        return productService.deleteProduct(productDTO);
    }
}
