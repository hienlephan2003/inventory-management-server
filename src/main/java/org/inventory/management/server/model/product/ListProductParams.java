package org.inventory.management.server.model.product;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class ListProductParams {
    private String query;
    private int pageSize = 10;
    private int pageNumber = 0;
}
