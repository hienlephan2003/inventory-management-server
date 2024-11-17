package org.inventory.management.server.model.inboundReport;

import lombok.Data;
import org.inventory.management.server.model.inboundReportDetail.UpsertInboundReportDetailModel;
import org.inventory.management.server.model.shipment.UpsertShipmentModel;

import java.util.Date;
import java.util.List;

@Data
public class InboundReportModel {
    private Date date;
    private int quantity;
    private UpsertShipmentModel shipment;
    private List<UpsertInboundReportDetailModel> items;
}
