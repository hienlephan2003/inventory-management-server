package org.inventory.management.server.service.stock;

import org.inventory.management.server.entity.StockReport;
import org.inventory.management.server.model.product.ListProductRes;
import org.inventory.management.server.model.product.ProductModelRes;
import org.inventory.management.server.model.product.UpsertProductModel;
import org.inventory.management.server.model.query.ListQueryParam;

import java.util.Map;

public interface StockReportService {
     StockReport getStockReportOfMonth();
     Map<String, Double> getCategoryPercentages();
}
