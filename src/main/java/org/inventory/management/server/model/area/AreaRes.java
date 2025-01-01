package org.inventory.management.server.model.area;

import lombok.*;
import org.inventory.management.server.model.category.CategoryRes;
import org.inventory.management.server.model.tag.TagModelRes;
import org.inventory.management.server.model.tag.TagModelResWithoutArea;

import java.util.List;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AreaRes {
    private long id;
    private String name;
    private List<TagModelResWithoutArea> tags;
}
