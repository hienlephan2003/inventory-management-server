package org.inventory.management.server.model.query;

import lombok.Data;
@Data
public class ListQueryParam {
    private String query;
    private int pageSize = 10;
    private int pageNumber = 0;

}
