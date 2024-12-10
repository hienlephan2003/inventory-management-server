package org.inventory.management.server.model.outboundReport;

import lombok.Data;
import org.inventory.management.server.entity.Shipment;
import org.inventory.management.server.model.inboundReportDetail.InboundReportDetailModelRes;
import org.inventory.management.server.model.outboundReportDetail.OutboundReportDetailModelRes;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OutboundReportModelRes {
    private Long id;
    private Date date;
    private int quantity;
    private Shipment shipment;
    private List<OutboundReportDetailModelRes> items;
    private BigDecimal totalPrice;
}
