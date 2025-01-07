package org.inventory.management.server.service.stock;

import org.inventory.management.server.entity.StockReport;
import org.inventory.management.server.entity.StockReportDetail;
import org.inventory.management.server.model.product.ListProductRes;
import org.inventory.management.server.model.product.ProductModelRes;
import org.inventory.management.server.model.product.UpsertProductModel;
import org.inventory.management.server.model.query.ListQueryParam;
import org.inventory.management.server.model.statistic.ChartData;
import org.inventory.management.server.model.stock.StockReportModelRes;
import org.inventory.management.server.model.stock.StockReportRequest;
import org.inventory.management.server.model.stockDetail.StockReportDetailModelRes;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface StockReportService {
     StockReport getStockReportOfThisMonth();
     List<Map<String, Object>> getCategoryPercentages();
     StockReportModelRes createStockReport(StockReportRequest request);
     StockReportModelRes getStockReport(StockReportRequest request);
     StockReportDetailModelRes createStockReportDetail(StockReportRequest request);
     List<ChartData> generateChartData();
     List<StockReportModelRes> getStockReports();
     StockReportModelRes getStockReportById(Long id);
     StockReport getStockReportOfLastMonth();
     List<ChartData> generateChartData(Date startDate, Date endDate);
}
