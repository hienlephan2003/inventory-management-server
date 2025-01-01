package org.inventory.management.server.model.tag;

import lombok.Data;

import java.util.Date;

@Data
public class TagModelResWithoutArea {
    private Long id;
    private String name;
    private String description;
    private Date createdDate;
}
