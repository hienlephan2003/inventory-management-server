package org.inventory.management.server.model.area;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AreaRequest {
    @NotEmpty(message = "Area's name cannot be empty")
    private String name;
}
