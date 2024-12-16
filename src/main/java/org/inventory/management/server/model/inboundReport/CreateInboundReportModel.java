package org.inventory.management.server.model.inboundReport;

import lombok.Data;
import org.inventory.management.server.model.inboundReportDetail.CreateInboundReportDetailModel;
import org.inventory.management.server.model.shipment.UpsertInboundReportShipmentModel;
import org.inventory.management.server.model.shipment.UpsertShipmentModel;

import java.util.Date;
import java.util.List;

@Data
public class CreateInboundReportModel {
    private Date date;
    private UpsertInboundReportShipmentModel shipment;
    private List<CreateInboundReportDetailModel> items;
}
