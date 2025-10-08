package com.heitor.catalog.catalog_service.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Table
@Entity
public class Product {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private BigDecimal pricce;

    @Column
    private Integer stockQuantity;

}
