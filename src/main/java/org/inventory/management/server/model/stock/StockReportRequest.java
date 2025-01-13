package org.inventory.management.server.model.stock;

import lombok.Data;
import org.inventory.management.server.model.enumeratiion.StockReportType;

import java.util.Date;

@Data
public class StockReportRequest {
    private Date startDate;
    private Date endDate;
    private String name;
    private Long productId;
    private StockReportType stockReportType;
}
