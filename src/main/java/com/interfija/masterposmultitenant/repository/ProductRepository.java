package com.interfija.masterposmultitenant.repository;


import com.interfija.masterposmultitenant.model.tenant.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}