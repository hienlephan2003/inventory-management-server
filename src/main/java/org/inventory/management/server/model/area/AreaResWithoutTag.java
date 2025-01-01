package org.inventory.management.server.model.area;

import lombok.*;
import org.inventory.management.server.model.tag.TagModelRes;

import java.util.List;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AreaResWithoutTag {
    private long id;
    private String name;
}
