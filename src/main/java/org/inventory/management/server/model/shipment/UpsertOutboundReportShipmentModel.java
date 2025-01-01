package org.inventory.management.server.model.shipment;

import lombok.Data;

import java.util.Date;

@Data
public class UpsertOutboundReportShipmentModel {
    private Date date;
    private String carrier;
    private Long employeeId;
    private String fromPosition;
    private String toPosition;
}
