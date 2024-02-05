package com.programmingtechie.orderservice.model;

import java.math.BigDecimal;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "t_order_line_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)/*GenerationType.IDENTITY: This strategy delegates the generation of primary key values to the underlying database's identity column mechanism. It is typically used with databases that support auto-increment or identity columns (e.g., MySQL, PostgreSQL). gonna be auto incremented */
    private long id;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
