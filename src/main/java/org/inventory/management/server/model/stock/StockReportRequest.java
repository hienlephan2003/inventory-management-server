package org.inventory.management.server.model.stock;

import lombok.Data;

import java.util.Date;

@Data
public class StockReportRequest {
    private Date startDate;
    private Date endDate;
    private String name;
    private Long productId;
}
