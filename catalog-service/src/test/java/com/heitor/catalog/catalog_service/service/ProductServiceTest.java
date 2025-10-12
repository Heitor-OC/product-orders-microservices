package com.heitor.catalog.catalog_service.service;

import com.heitor.catalog.catalog_service.dto.ProductDTO;
import com.heitor.catalog.catalog_service.model.Product;
import com.heitor.catalog.catalog_service.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        product = new Product();
        product.setId(UUID.randomUUID());
        product.setName("Notebook");
        product.setDescription("ASUS");
        product.setPrice(BigDecimal.valueOf(2750.00));
        product.setStockQuantity(10);
    }

    @Test
    void deveCriarProdutoComSucesso() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDTO dto = new ProductDTO(null, "Notebook", "ASUS", BigDecimal.valueOf(2750.00), 10);
        ProductDTO result = productService.create(dto);

        assertNotNull(result);
        assertEquals("Notebook", result.getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void deveListarTodosOsProdutos() {
        when(productRepository.findAll()).thenReturn(Collections.singletonList(product));
        assertFalse(productService.findAll().isEmpty());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void deveBuscarProdutoPorId() {
        when(productRepository.findById(any(UUID.class))).thenReturn(Optional.of(product));

        Optional<ProductDTO> dto = productService.findById(UUID.randomUUID());
        assertNotNull(dto);
        assertEquals("Notebook", dto.get().getName());
    }

}