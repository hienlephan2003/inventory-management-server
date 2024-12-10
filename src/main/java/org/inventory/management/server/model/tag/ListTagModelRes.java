package org.inventory.management.server.model.tag;

import lombok.Data;

import java.util.List;

@Data
public class ListTagModelRes {
    private List<TagModelRes> tags;
}
