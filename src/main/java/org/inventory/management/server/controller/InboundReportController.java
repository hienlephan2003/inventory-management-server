package org.inventory.management.server.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.inventory.management.server.model.inboundReport.CreateInboundReportModel;
import org.inventory.management.server.model.inboundReport.InboundReportModelRes;
import org.inventory.management.server.model.inboundReport.ListDataRes;
import org.inventory.management.server.model.inboundReport.UpdateInboundReportModel;
import org.inventory.management.server.service.inboundReport.InboundReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController()
@RequestMapping("/api/inboundReports")
@AllArgsConstructor
public class InboundReportController {
    private final InboundReportService inboundReportService;
    @GetMapping("/{id}")
    ResponseEntity<InboundReportModelRes> getInboundReportById(@PathVariable Long id){
        return ResponseEntity.ok(inboundReportService.getInboundReportById(id));
    }
    @GetMapping("")
    ResponseEntity<ListDataRes<InboundReportModelRes>> getInboundReports(){
        return ResponseEntity.ok(inboundReportService.getInboundReports());
    }
    @PostMapping
    ResponseEntity<InboundReportModelRes> createInboundReport(@Valid @RequestBody CreateInboundReportModel inboundReportModel){
        return ResponseEntity.ok(inboundReportService.createInboundReport(inboundReportModel));
    }
    @PutMapping("/{id}")
    ResponseEntity<InboundReportModelRes> updateInboundReport(@PathVariable Long id, @Valid @RequestBody UpdateInboundReportModel inboundReportModel){
        return ResponseEntity.ok(inboundReportService.updateInboundReport(id, inboundReportModel));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<InboundReportModelRes> deleteInboundReport(@PathVariable Long id) {

        return ResponseEntity.ok(inboundReportService.deleteInboundReport(id));
    }
}
