package org.inventory.management.server.model.inventoryCheck;


import lombok.Data;

import java.util.List;
@Data
public class ListDataRes<T> {
    private List<T> data;
    private int total;
}
