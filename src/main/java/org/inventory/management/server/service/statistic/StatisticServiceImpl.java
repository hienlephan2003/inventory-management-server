package org.inventory.management.server.service.statistic;

import lombok.AllArgsConstructor;
import org.inventory.management.server.entity.StockReport;
import org.inventory.management.server.model.statistic.StatisticModelRes;
import org.inventory.management.server.service.stock.StockReportService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private final StockReportService stockReportService;
    @Override
    public StatisticModelRes getStatisticInformation() {
        StatisticModelRes statisticModelRes = new StatisticModelRes();
        StockReport stockReport = stockReportService.getStockReportOfMonth();
        stockReport.getItems().stream().map(
                item -> {
                    statisticModelRes.setStockNumber(statisticModelRes.getStockNumber() + item.getQuantity());
                    return null;
                }
        );
        return statisticModelRes;
    }
}
