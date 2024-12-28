package org.inventory.management.server.controller;

import lombok.AllArgsConstructor;
import org.inventory.management.server.model.statistic.WeekStatistic;
import org.inventory.management.server.model.stock.StockReportModelRes;
import org.inventory.management.server.service.inboundReport.InboundReportService;
import org.inventory.management.server.service.outboundReport.OutboundReportService;
import org.inventory.management.server.service.stock.StockReportService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController()
@RequestMapping("/api/statistics")
@AllArgsConstructor
public class StatisticController {
    private final StockReportService stockReportService;
    private final ModelMapper mapper;
    private final InboundReportService inboundReportService;
    private final OutboundReportService outboundReportService;
    @GetMapping("")
    ResponseEntity<StockReportModelRes> getReportOfMonth(){
        return ResponseEntity.ok(mapper.map(stockReportService.getStockReportOfMonth(), StockReportModelRes.class));
    }
    @GetMapping("/week")
    public ResponseEntity<WeekStatistic> getCurrentWeekQuantitiesByDate() {
        WeekStatistic statistic = new WeekStatistic();
        statistic.setInbounds(inboundReportService.getCurrentWeekQuantitiesByDate());
        statistic.setOutbounds(outboundReportService.getCurrentWeekQuantitiesByDate());
        return ResponseEntity.ok(statistic);
    }
    @GetMapping("/category-percentages")
    public Map<String, Double> getCategoryPercentages() {
        return stockReportService.getCategoryPercentages();
    }
}