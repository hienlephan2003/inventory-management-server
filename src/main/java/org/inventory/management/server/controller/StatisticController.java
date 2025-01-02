package org.inventory.management.server.controller;

import lombok.AllArgsConstructor;
import org.inventory.management.server.model.statistic.ChartData;
import org.inventory.management.server.model.statistic.StatisticModelRes;
import org.inventory.management.server.model.statistic.WeekStatistic;
import org.inventory.management.server.model.stock.StockReportModelRes;
import org.inventory.management.server.service.inboundReport.InboundReportService;
import org.inventory.management.server.service.outboundReport.OutboundReportService;
import org.inventory.management.server.service.statistic.StatisticService;
import org.inventory.management.server.service.stock.StockReportService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/api/statistics")
@AllArgsConstructor
public class StatisticController {
    private final StockReportService stockReportService;
    private final ModelMapper mapper;
    private final InboundReportService inboundReportService;
    private final OutboundReportService outboundReportService;
    private final StatisticService statisticService;
    @GetMapping("")
    ResponseEntity<StatisticModelRes> getReportOfMonth(){
        return ResponseEntity.ok(statisticService.getStatisticInformation());
    }
    @GetMapping("/lastmonth")
    ResponseEntity<StockReportModelRes> getReportOfLastMonth(){
        return ResponseEntity.ok(mapper.map(stockReportService.getStockReportOfLastMonth(), StockReportModelRes.class));
    }
    @GetMapping("/week")
    public ResponseEntity<List<ChartData>> getCurrentWeekQuantitiesByDate() {
       return ResponseEntity.ok(stockReportService.generateChartData());
    }
    @GetMapping("/category-percentages")
    public List<Map<String, Object>> getCategoryPercentages() {
        return stockReportService.getCategoryPercentages();
    }
}