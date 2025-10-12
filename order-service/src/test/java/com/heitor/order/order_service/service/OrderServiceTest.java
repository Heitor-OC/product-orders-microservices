package com.heitor.orders.order_service.service;

import com.heitor.orders.order_service.client.CatalogClient;
import com.heitor.orders.order_service.dto.OrderDTO;
import com.heitor.orders.order_service.dto.OrderItemDTO;
import com.heitor.orders.order_service.dto.ProductDTO;
import com.heitor.orders.order_service.model.Order;
import com.heitor.orders.order_service.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CatalogClient catalogClient;

    @InjectMocks
    private OrderService orderService;

    private ProductDTO product;
    private Order order;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        product = new ProductDTO(UUID.randomUUID(), "Notebook", "ASUS", BigDecimal.valueOf(99999.00), 10);

        order = new Order();
        order.setId(UUID.randomUUID());
        order.setStatus("CREATED");
    }

    @Test
    void deveCriarPedidoComSucesso() {
        OrderItemDTO itemDTO = new OrderItemDTO(product.getId(), 2, BigDecimal.valueOf(99999.00));
        OrderDTO dto = new OrderDTO(null, null, null, Collections.singletonList(itemDTO));

        when(catalogClient.getProductById(product.getId())).thenReturn(product);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDTO result = orderService.create(dto);

        assertNotNull(result);
        assertEquals("CREATED", result.getStatus());
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(catalogClient, times(1)).getProductById(product.getId());
    }
}
