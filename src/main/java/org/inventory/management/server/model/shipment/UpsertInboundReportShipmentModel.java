package org.inventory.management.server.model.shipment;

import lombok.Data;
import org.inventory.management.server.model.enumeratiion.ShipmentStatus;
import org.inventory.management.server.model.enumeratiion.ShipmentType;

import java.util.Date;

@Data
public class UpsertInboundReportShipmentModel {
    private Date date;
    private String carrier;
    private Long employeeId;
    private String fromPosition;
    private String toPosition;
}
