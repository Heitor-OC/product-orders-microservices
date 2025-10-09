package com.heitor.order.order_service.service;

import com.heitor.order.order_service.dto.OrderDTO;
import com.heitor.order.order_service.dto.OrderItemDTO;
import com.heitor.order.order_service.model.Order;
import com.heitor.order.order_service.model.OrderItem;
import com.heitor.order.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderDTO create(OrderDTO dto) {
        Order order = new Order();
        order.setStatus("CREATED");
        order.setItems(dto.getItems().stream().map(this::toEntity).collect(Collectors.toList()));

        Order savedOrder = orderRepository.save(order);
        return toDto(savedOrder);
    }

    public List<OrderDTO> findAll() {
        return orderRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public OrderDTO findById(UUID id) {
        return orderRepository.findById(id).map(this::toDto).orElse(null);
    }

    public void delete(UUID id) {
        orderRepository.deleteById(id);
    }

    public OrderDTO update(UUID id, OrderDTO dto) {
        return orderRepository.findById(id).map(o -> {
            o.setStatus(dto.getStatus() != null ? dto.getStatus() : o.getStatus());
            o.setItems(dto.getItems().stream().map(this::toEntity).collect(Collectors.toList()));
            Order updatedOrder = orderRepository.save(o);
            return toDto(updatedOrder);
        }).orElse(null);
    }

    private OrderDTO toDto(Order order) {
        return new OrderDTO(order.getId(),
        order.getCreatedAt(),
        order.getStatus(),
        order.getItems().stream().map(i -> new OrderItemDTO(i.getProductId(), i.getQuantity(), i.getPrice())).collect(Collectors.toList()));
    }

    private OrderItem toEntity(OrderItemDTO dto) {
        OrderItem item = new OrderItem();
        item.setProductId(dto.getProductId());
        item.setQuantity(dto.getQuantity());
        item.setPrice(dto.getPrice());
        return item;
    }

}
