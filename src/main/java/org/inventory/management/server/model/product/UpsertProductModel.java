package org.inventory.management.server.model.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class UpsertProductModel {
    private Long id;
    @NotBlank(message = "Name is required")
    private String name;
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0", message = "Price must be greater than 0")
    private BigDecimal price;
    private String description;
    private String imageUrl;
}
