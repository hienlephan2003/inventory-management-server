package org.inventory.management.server.service.inboundReport;

import org.inventory.management.server.entity.OutboundReportDetail;
import org.inventory.management.server.model.inboundReport.CreateInboundReportModel;
import org.inventory.management.server.model.inboundReport.InboundReportModelRes;
import org.inventory.management.server.model.inboundReport.UpdateInboundReportModel;
import org.inventory.management.server.model.inboundReportDetail.InboundReportDetailModelRes;

import java.util.List;

public interface InboundReportService {
    InboundReportModelRes getInboundReportById(long id) ;
    InboundReportModelRes updateInboundReport(long id, UpdateInboundReportModel InboundReportModel);
    InboundReportModelRes createInboundReport(CreateInboundReportModel InboundReportModel);
    InboundReportModelRes deleteInboundReport(long id);
    List<InboundReportDetailModelRes> updateStockQuantity(OutboundReportDetail outboundReportDetail);
}
