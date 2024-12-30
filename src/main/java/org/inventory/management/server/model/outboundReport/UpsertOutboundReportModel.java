package org.inventory.management.server.model.outboundReport;

import lombok.Data;
import org.inventory.management.server.model.outboundReportDetail.UpsertOutboundReportDetailModel;
import org.inventory.management.server.model.shipment.UpsertInboundReportShipmentModel;
import org.inventory.management.server.model.shipment.UpsertShipmentModel;

import java.util.Date;
import java.util.List;

@Data
public class UpsertOutboundReportModel {
    private Date date;
    private UpsertInboundReportShipmentModel shipment;
    private List<UpsertOutboundReportDetailModel> items;
}
