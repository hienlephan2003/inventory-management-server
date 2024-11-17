package org.inventory.management.server.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.inventory.management.server.model.inboundReport.InboundReportModelRes;
import org.inventory.management.server.model.inboundReport.UpsertInboundReportModel;
import org.inventory.management.server.service.inboundReport.InboundReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/inboundReports")
@AllArgsConstructor
public class InboundReportController {
    private final InboundReportService inboundReportService;
    @GetMapping("/{id}")
    ResponseEntity<InboundReportModelRes> getInboundReportById(@PathVariable Long id){
        return ResponseEntity.ok(inboundReportService.getInboundReportById(id));
    }
    @PostMapping
    ResponseEntity<InboundReportModelRes> createInboundReport(@Valid @RequestBody UpsertInboundReportModel inboundReportModel){
        return ResponseEntity.ok(inboundReportService.createInboundReport(inboundReportModel));
    }
    @PutMapping("/{id}")
    ResponseEntity<InboundReportModelRes> updateInboundReport(@PathVariable Long id, @Valid @RequestBody UpsertInboundReportModel inboundReportModel){
        return ResponseEntity.ok(inboundReportService.updateInboundReport(id, inboundReportModel));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<InboundReportModelRes> deleteInboundReport(@PathVariable Long id) {
        return ResponseEntity.ok(inboundReportService.deleteInboundReport(id));
    }
}
