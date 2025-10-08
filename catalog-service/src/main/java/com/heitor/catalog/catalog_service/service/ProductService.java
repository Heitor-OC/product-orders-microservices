package com.heitor.catalog.catalog_service.service;

import com.heitor.catalog.catalog_service.dto.ProductDTO;
import com.heitor.catalog.catalog_service.model.Product;
import com.heitor.catalog.catalog_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductDTO create(ProductDTO dto) {
        Product product = new Product();

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());

        product = productRepository.save(product);

        dto.setId(product.getId());
        return dto;
    }

    public List<ProductDTO> findAll() {
        return productRepository.findAll()
                .stream()
                .map(product -> new ProductDTO(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getStockQuantity()))
                .collect(Collectors.toList());
    }

}
