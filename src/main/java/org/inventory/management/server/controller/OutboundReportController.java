package org.inventory.management.server.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.inventory.management.server.model.inboundReport.InboundReportModelRes;
import org.inventory.management.server.model.inboundReport.ListDataRes;
import org.inventory.management.server.model.outboundReport.OutboundReportModelRes;
import org.inventory.management.server.model.outboundReport.UpsertOutboundReportModel;
import org.inventory.management.server.service.outboundReport.OutboundReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/outboundReports")
@AllArgsConstructor
public class OutboundReportController {
    private final OutboundReportService outboundReportService;
    @GetMapping("/{id}")
    ResponseEntity<OutboundReportModelRes> getOutboundReportById(@PathVariable Long id){
        return ResponseEntity.ok(outboundReportService.getOutboundReportById(id));
    }
    @GetMapping("")
    ResponseEntity<ListDataRes<OutboundReportModelRes>> getInboundReports(){
        return ResponseEntity.ok(outboundReportService.getOutboundReports());
    }

    @PostMapping
    ResponseEntity<OutboundReportModelRes> createOutboundReport(@Valid @RequestBody UpsertOutboundReportModel outboundReportModel){
        return ResponseEntity.ok(outboundReportService.createOutboundReport(outboundReportModel));
    }
    @PutMapping("/{id}")
    ResponseEntity<OutboundReportModelRes> updateOutboundReport(@PathVariable Long id, @Valid @RequestBody UpsertOutboundReportModel outboundReportModel){
        return ResponseEntity.ok(outboundReportService.updateOutboundReport(id, outboundReportModel));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<OutboundReportModelRes> deleteOutboundReport(@PathVariable Long id) {
        return ResponseEntity.ok(outboundReportService.deleteOutboundReport(id));
    }
}
