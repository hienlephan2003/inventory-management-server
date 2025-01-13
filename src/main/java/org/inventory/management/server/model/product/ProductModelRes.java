package org.inventory.management.server.model.product;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.inventory.management.server.entity.Tag;
import org.inventory.management.server.model.category.CategoryRes;
import org.inventory.management.server.model.company.CompanyRes;
import org.inventory.management.server.model.enumeratiion.ProductType;
import org.inventory.management.server.model.inboundReportDetail.InboundReportDetailModelRes;
import org.inventory.management.server.model.tag.TagModelRes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
public class ProductModelRes {
    private String name;
    private Long id;
    private String sku;
    private BigDecimal marketPrice;
    private BigDecimal productionCost;
    private String image;
    private int minQuantity;
    private int maxQuantity;
    private CategoryRes category;
    private CompanyRes company;
    private String description;
    private Set<TagModelRes> tags;
    private List<InboundReportDetailModelRes> items;
    private int quantity;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductType type; // Loại (Dạng kem, Dạng xịt, Dạng lotion, Dạng bột…)
    private String capacity; // Dung tích (Đối với Dạng kem, Lotion…)
    private String weight; // Trọng lượng (Đối với Dạng bột…)
    private String color; // Màu sắc
    private String size; // Kích thước
    private String volume; // Dung lượng
}