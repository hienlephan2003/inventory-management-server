package org.inventory.management.server.service.stockDetail;


import org.inventory.management.server.entity.InboundReportDetail;
import org.inventory.management.server.entity.OutboundReportDetail;
import org.inventory.management.server.entity.Product;
import org.inventory.management.server.entity.StockReportDetail;

public interface StockReportDetailService {
     StockReportDetail getStockReportDetailOfMonth(Long productId);
     StockReportDetail onInboundReport(InboundReportDetail inboundReportDetail);
     StockReportDetail onOutboundReport(OutboundReportDetail outboundReportDetail);
}
