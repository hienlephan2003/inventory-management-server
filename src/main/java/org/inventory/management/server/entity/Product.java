package org.inventory.management.server.entity;

import jakarta.persistence.*;
import lombok.*;
import org.inventory.management.server.model.enumeratiion.ProductType;

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
    @Enumerated(EnumType.STRING)
    private ProductType type; // Loại (Dạng kem, Dạng xịt, Dạng lotion, Dạng bột…)
    private String capacity; // Dung tích (Đối với Dạng kem, Lotion…)
    private String weight; // Trọng lượng (Đối với Dạng bột…)
    private String color; // Màu sắc
    private String size; // Kích thước
    private String volume; // Dung lượng
}
