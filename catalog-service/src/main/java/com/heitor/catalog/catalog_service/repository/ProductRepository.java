package com.heitor.catalog.catalog_service.repository;

import com.heitor.catalog.catalog_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
