package org.inventory.management.server.model.inboundReport;

import lombok.Data;
import org.inventory.management.server.model.inboundReportDetail.UpsertInboundReportDetailModel;
import org.inventory.management.server.model.shipment.UpsertShipmentModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class UpsertInboundReportModel {
    private Long id;
    private Date date;
    private int quantity;
    private BigDecimal price;
    private UpsertShipmentModel shipment;
    private List<UpsertInboundReportDetailModel> items;
}
