package org.inventory.management.server.model.inboundReport;

import lombok.Data;
import org.inventory.management.server.entity.InboundReportDetail;
import org.inventory.management.server.entity.Shipment;
import org.inventory.management.server.model.inboundReportDetail.InboundReportDetailModelRes;
import org.inventory.management.server.model.shipment.UpsertShipmentModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class InboundReportModelRes {
    private Long id;
    private Date date;
    private int quantity;
    private BigDecimal price;
    private Shipment shipment;
    private List<InboundReportDetailModelRes> items;
}
