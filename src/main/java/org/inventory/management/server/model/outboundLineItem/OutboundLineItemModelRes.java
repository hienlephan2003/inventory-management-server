package org.inventory.management.server.model.outboundLineItem;

import lombok.Data;
import org.inventory.management.server.model.inboundReportDetail.InboundReportDetailModelRes;

@Data
public class OutboundLineItemModelRes {
    private Long id;
    private int quantity;
    private InboundReportDetailModelRes inboundReportDetail;
}
