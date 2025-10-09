package com.heitor.order.order_service.model;

import com.sun.jdi.IntegerValue;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID productId;
    private IntegerValue quantity;
    private BigDecimal price;
}
