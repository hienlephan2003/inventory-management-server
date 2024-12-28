package org.inventory.management.server.service.outboundReport;

import org.inventory.management.server.model.outboundReport.OutboundReportModelRes;
import org.inventory.management.server.model.outboundReport.UpsertOutboundReportModel;

import java.util.Map;

public interface OutboundReportService {
    OutboundReportModelRes getOutboundReportById(long id) ;
    OutboundReportModelRes updateOutboundReport(long id, UpsertOutboundReportModel OutboundReportModel);
    OutboundReportModelRes createOutboundReport(UpsertOutboundReportModel OutboundReportModel);
    OutboundReportModelRes deleteOutboundReport(long id);
    Map<String, Integer> getCurrentWeekQuantitiesByDate();
}