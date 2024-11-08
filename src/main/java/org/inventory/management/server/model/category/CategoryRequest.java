package org.inventory.management.server.model.category;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    @NotEmpty(message = "Category's name cannot be empty")
    private String name;
}
