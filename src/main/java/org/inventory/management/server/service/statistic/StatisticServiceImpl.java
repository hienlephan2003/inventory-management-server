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
    public StatisticModelRes getStatisticInformation() {
        StatisticModelRes statisticModelRes = new StatisticModelRes();

        // Fetch stock reports for this month and last month
        StockReport stockReport = stockReportService.getStockReportOfThisMonth();
        StockReport stockReportOfLastMonth = stockReportService.getStockReportOfLastMonth();

        // Current stock and inbound numbers
        int currentStock = stockReport.getStockQuantity();
        int currentInbound = stockReport.getInboundQuantity();
        int currentInboundNeeded = stockReport.getNeedInboundQuantity();

        // Last month's stock and inbound numbers (avoid division by zero)
        int lastStock = Math.max(stockReportOfLastMonth.getStockQuantity(), 1);
        int lastInbound = Math.max(stockReportOfLastMonth.getInboundQuantity(), 1);
        int lastInboundNeeded = Math.max(stockReportOfLastMonth.getNeedInboundQuantity(), 1);

        // Calculate percentages based on last month's values
        double stockPercent = (currentStock * 100.0) / lastStock;
        double inboundPercent = (currentInbound * 100.0) / lastInbound;
        double inboundNeededPercent = (currentInboundNeeded * 100.0) / lastInboundNeeded;

        // Calculate the percentage change (increase or decrease)
        String stockPercentChange = calculatePercentageChange(stockPercent);
        String inboundPercentChange = calculatePercentageChange(inboundPercent);
        String inboundNeededPercentChange = calculatePercentageChange(inboundNeededPercent);

        // Set values in the StatisticModelRes object
        statisticModelRes.setStockNumber(currentStock);
        statisticModelRes.setIndboundNumber(currentInbound);
        statisticModelRes.setInboundNeededNumber(currentInboundNeeded);
        statisticModelRes.setStockPercent(stockPercent);
        statisticModelRes.setInboundPercent(inboundPercent);
        statisticModelRes.setInboundNeededPercent(inboundNeededPercent);

        // Set the percentage change with the increase or decrease label and actual percentage difference
        statisticModelRes.setStockPercentChange(stockPercentChange);
        statisticModelRes.setInboundPercentChange(inboundPercentChange);
        statisticModelRes.setInboundNeededPercentChange(inboundNeededPercentChange);

        return statisticModelRes;
    }

    private String calculatePercentageChange(double percentage) {
        if (percentage > 100) {
            // Calculate the increase
            double increase = percentage - 100;
            return "Tăng " + String.format("%.2f", increase) + "%";
        } else if (percentage < 100) {
            // Calculate the decrease
            double decrease = 100 - percentage;
            return "Giảm " + String.format("%.2f", decrease) + "%";
        } else {
            // No change
            return "Không thay đổi";
        }
    }
}
