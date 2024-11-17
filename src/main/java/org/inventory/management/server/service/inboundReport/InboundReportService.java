package org.inventory.management.server.service.inboundReport;

import org.inventory.management.server.model.inboundReport.InboundReportModelRes;
import org.inventory.management.server.model.inboundReport.UpsertInboundReportModel;

public interface InboundReportService {
    InboundReportModelRes getInboundReportById(long id) ;
    InboundReportModelRes updateInboundReport(long id, UpsertInboundReportModel InboundReportModel);
    InboundReportModelRes createInboundReport(UpsertInboundReportModel InboundReportModel);
    InboundReportModelRes deleteInboundReport(long id);
}
