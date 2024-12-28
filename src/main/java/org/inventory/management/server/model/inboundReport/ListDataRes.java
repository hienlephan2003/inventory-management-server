package org.inventory.management.server.model.inboundReport;


import lombok.Data;

import java.util.List;
@Data
public class ListDataRes<T> {
    private List<T> data;
    private int total;
}
