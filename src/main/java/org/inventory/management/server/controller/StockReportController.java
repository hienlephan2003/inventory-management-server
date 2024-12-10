package org.inventory.management.server.controller;

import lombok.AllArgsConstructor;
import org.inventory.management.server.model.stock.StockReportModelRes;
import org.inventory.management.server.service.stock.StockReportService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/stocks")
@AllArgsConstructor
public class StockReportController {
    private final StockReportService stockReportService;
    private final ModelMapper mapper;
    @GetMapping("")
    ResponseEntity<StockReportModelRes> getReportOfMonth(){
        return ResponseEntity.ok(mapper.map(stockReportService.getStockReportOfMonth(), StockReportModelRes.class));
    }
}