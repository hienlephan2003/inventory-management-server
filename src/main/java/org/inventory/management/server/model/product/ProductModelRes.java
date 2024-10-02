package org.inventory.management.server.model.product;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductModelRes {
    private String name;
    private BigDecimal price;
    private String description;
    private String imageUrl;
}
