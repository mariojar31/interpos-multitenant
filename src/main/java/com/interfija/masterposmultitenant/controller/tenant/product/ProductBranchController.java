package com.interfija.masterposmultitenant.controller.tenant.product;

import com.interfija.masterposmultitenant.dto.product.ProductBranchStockDTO;
import com.interfija.masterposmultitenant.services.product.ProductBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Controlador REST para gestionar el stock de productos por sucursal.
 * Permite consultar el stock actual, actualizarlo o transferirlo entre sucursales.
 *
 * Autor: OpenAI para Steven Arzuza
 */
@RestController
@RequestMapping("/api/tenant/product-branch")
public class ProductBranchController {

    private final ProductBranchService productBranchService;

    @Autowired
    public ProductBranchController(ProductBranchService productBranchService) {
        this.productBranchService = productBranchService;
    }

    /**
     * Obtiene el stock por ID de producto en todas las sucursales.
     *
     * @param productId ID del producto
     * @return Lista de stocks por sucursal
     */
    @GetMapping("/stock/{productId}")
    public List<ProductBranchStockDTO> getStockByProductId(@PathVariable long productId) {
        return productBranchService.getStockByProductId(productId);
    }

    /**
     * Actualiza la cantidad de stock en una sucursal específica.
     *
     * @param idProductBranch ID de la relación producto-sucursal
     * @param quantity Nueva cantidad de stock
     * @return true si se actualizó correctamente, false si no
     */
    @PutMapping("/stock/update")
    public boolean updateStock(@RequestParam BigDecimal quantity, @RequestParam long idProductBranch) {
        return productBranchService.updateStock(quantity, idProductBranch);
    }

    /**
     * Transfiere stock entre dos sucursales del mismo producto.
     *
     * @param quantity Cantidad a transferir
     * @param fromId ID de la sucursal origen
     * @param toId ID de la sucursal destino
     * @return true si fue exitoso, false si falló
     */
    @PostMapping("/stock/transfer")
    public boolean transferStock(@RequestParam BigDecimal quantity,
                                 @RequestParam long fromId,
                                 @RequestParam long toId) {
        return productBranchService.transferStock(quantity, fromId, toId);
    }
}
