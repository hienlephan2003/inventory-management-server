package org.inventory.management.server.model.product;

import lombok.Data;
import org.inventory.management.server.entity.Product;

import java.util.List;

@Data
public class ListProductRes {
    private List<ProductModelRes> productList;
    private int total;
}
