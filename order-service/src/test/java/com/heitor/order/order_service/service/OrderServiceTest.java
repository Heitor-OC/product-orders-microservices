package com.heitor.order.order_service.service;


import com.heitor.order.order_service.dto.OrderDTO;
import com.heitor.order.order_service.dto.OrderItemDTO;
import com.heitor.order.order_service.model.Order;
import com.heitor.order.order_service.model.OrderItem;
import com.heitor.order.order_service.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Order order;
    private OrderDTO orderDTO;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        OrderItem item = new OrderItem();
        item.setProductId(UUID.randomUUID());
        item.setQuantity(2);
        item.setPrice(BigDecimal.valueOf(50));

        order = new Order();
        order.setId(UUID.randomUUID());
        order.setStatus("CREATED");
        order.setItems(Collections.singletonList(item));

        OrderItemDTO itemDTO = new OrderItemDTO(item.getProductId(), 2, BigDecimal.valueOf(50));
        orderDTO = new OrderDTO(order.getId(), null, "CREATED", Collections.singletonList(itemDTO));
    }

    @Test
    void deveCriarPedido() {
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDTO result = orderService.create(orderDTO);

        assertNotNull(result);
        assertEquals("CREATED", result.getStatus());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void deveBuscarPedidoPorId() {
        when(orderRepository.findById(any(UUID.class))).thenReturn(Optional.of(order));

        OrderDTO result = orderService.findById(order.getId());
        assertNotNull(result);
        assertEquals("CREATED", result.getStatus());
    }
}