package com.heitor.catalog.catalog_service.service;

import com.heitor.catalog.catalog_service.dto.ProductDTO;
import com.heitor.catalog.catalog_service.model.Product;
import com.heitor.catalog.catalog_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
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

    public Optional<ProductDTO> findById(UUID id) {
        return productRepository.findById(id).map(p -> new ProductDTO(p.getId(), p.getName(), p.getDescription(), p.getPrice(), p.getStockQuantity()));
    }

    public Optional<ProductDTO> update(UUID id, ProductDTO dto){
        return productRepository.findById(id).map(p -> {
            p.setName(dto.getName());
            p.setDescription(dto.getDescription());
            p.setPrice(dto.getPrice());
            p.setStockQuantity(dto.getStockQuantity());
            productRepository.save(p);
            dto.setId(p.getId());
            return dto;
        });
    }

    public void delete(UUID id) {
        productRepository.deleteById(id);
    }
}
