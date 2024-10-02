package org.inventory.management.server.model.product;

import lombok.Data;
import org.inventory.management.server.model.base.BaseModel;
import java.math.BigDecimal;

@Data
public class ProductModelRes extends BaseModel {
    private String name;
    private BigDecimal price;
    private String description;
    private String imageUrl;
}
