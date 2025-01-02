package org.inventory.management.server.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sku;
    private String name;
    private String description;
    private BigDecimal marketPrice;
    private BigDecimal productionCost;
    private String image;
    private Integer minQuantity;
    private Integer maxQuantity;
    private Integer quantity;
    private Boolean isDeleted;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    @ManyToMany(mappedBy = "products")
    private Set<Tag> tags = new HashSet<>();
}
