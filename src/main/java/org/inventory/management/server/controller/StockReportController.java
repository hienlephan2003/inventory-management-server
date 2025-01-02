package org.inventory.management.server.controller;

import lombok.AllArgsConstructor;
import org.inventory.management.server.model.statistic.ChartData;
import org.inventory.management.server.model.stock.StockReportModelRes;
import org.inventory.management.server.model.stock.StockReportRequest;
import org.inventory.management.server.model.stockDetail.StockReportDetailModelRes;
import org.inventory.management.server.service.stock.StockReportService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/stocks")
@AllArgsConstructor
public class StockReportController {
    private final StockReportService stockReportService;
    private final ModelMapper mapper;
//    @PostMapping("")
//    ResponseEntity<StockReportModelRes> getStockReport(@RequestBody StockReportRequest model){
//        return ResponseEntity.ok(mapper.map(stockReportService.getStockReport(model), StockReportModelRes.class));
//    }
    @PostMapping("/dateRange")
    ResponseEntity<List<ChartData>> getStockReportDateRange(@RequestBody StockReportRequest model){
        return ResponseEntity.ok(stockReportService.generateChartData(model.getStartDate(), model.getEndDate()));
    }

    @PostMapping("")
    ResponseEntity<StockReportModelRes> getReportOfMonth(@RequestBody StockReportRequest model){
        return ResponseEntity.ok(stockReportService.createStockReport(model));
    }
    @GetMapping("/detail/more")
    ResponseEntity<StockReportDetailModelRes> geeee(@RequestBody StockReportRequest model){
        return ResponseEntity.ok(stockReportService.createStockReportDetail(model));
    }
    @GetMapping("")
    ResponseEntity<List<StockReportModelRes>> getStockReports(){
        return ResponseEntity.ok(stockReportService.getStockReports());
    }
}