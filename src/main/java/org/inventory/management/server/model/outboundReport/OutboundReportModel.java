package org.inventory.management.server.model.outboundReport;

import lombok.Data;
import org.inventory.management.server.model.outboundReportDetail.UpsertOutboundReportDetailModel;
import org.inventory.management.server.model.shipment.UpsertShipmentModel;

import java.util.Date;
import java.util.List;

@Data
public class OutboundReportModel {
    private Date date;
    private int quantity;
    private UpsertShipmentModel shipment;
    private List<UpsertOutboundReportDetailModel> items;
}
