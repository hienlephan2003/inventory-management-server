package org.inventory.management.server.model.stock;

import lombok.Data;
import org.inventory.management.server.model.stockDetail.StockReportDetailModelRes;

import java.util.Date;
import java.util.List;

@Data
public class StockReportModelRes {
    private Long id;
    private Date date;
    private List<StockReportDetailModelRes> items;
}
