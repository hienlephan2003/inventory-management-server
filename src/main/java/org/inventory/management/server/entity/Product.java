package org.inventory.management.server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product extends  BaseEntity {
    private String name;
    private BigDecimal price;
    private String description;
    private String imageUrl;
}
