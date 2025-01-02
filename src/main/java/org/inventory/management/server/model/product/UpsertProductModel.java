package org.inventory.management.server.model.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.inventory.management.server.entity.Category;
import org.inventory.management.server.entity.Company;
import org.inventory.management.server.entity.Tag;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class UpsertProductModel {
    private String sku;
    @NotBlank(message = "Name is required")
    private String name;
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0", message = "Price must be greater than 0")
    private BigDecimal marketPrice;
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0", message = "Price must be greater than 0")
    private BigDecimal productionCost;
    private String image;
    private int minQuantity;
    private int maxQuantity;
    private long categoryId;
    private long companyId;
    private List<Long> tagIds;
    private String description;
    private int quantity;
}
