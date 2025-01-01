package org.inventory.management.server.model.tag;

import lombok.Data;

@Data
public class UpsertTagModel {
    private String name;
    private String description;
    private Long areaId;
}
