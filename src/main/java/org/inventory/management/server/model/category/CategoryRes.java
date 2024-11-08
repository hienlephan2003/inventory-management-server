package org.inventory.management.server.model.category;

import lombok.*;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRes {
    private long id;
    private String name;
}
