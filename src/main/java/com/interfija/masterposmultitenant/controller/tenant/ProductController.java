package com.interfija.masterposmultitenant.controller.tenant;

import com.interfija.masterposmultitenant.model.tenant.Product;
import com.interfija.masterposmultitenant.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository productoRepository;

    @GetMapping
    public List<Product> listarProductos() {
        return productoRepository.findAll();
    }

    @PostMapping
    public Product crearProducto(@RequestBody Product producto) {
        return productoRepository.save(producto);
    }
}
