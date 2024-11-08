package org.inventory.management.server.service.inboundReport;

import org.inventory.management.server.model.inboundReport.InboundReportModelRes;
import org.inventory.management.server.model.inboundReport.UpsertInboundReportModel;

public interface InboundReportService {
    InboundReportModelRes getInboundReportById(long id) ;
    InboundReportModelRes upsertInboundReport(UpsertInboundReportModel InboundReportModel);
    InboundReportModelRes deleteInboundReport(long id);
}
