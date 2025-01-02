package org.inventory.management.server.model.product;
import lombok.Data;
import org.inventory.management.server.entity.Tag;
import org.inventory.management.server.model.category.CategoryRes;
import org.inventory.management.server.model.company.CompanyRes;
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
}
