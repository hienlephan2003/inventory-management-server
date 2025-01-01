package org.inventory.management.server.model.shipment;

import lombok.Data;
import org.inventory.management.server.model.enumeratiion.ShipmentStatus;
import org.inventory.management.server.model.enumeratiion.ShipmentType;

import java.util.Date;

@Data
public class ShipmentModelRes {
    private int Id;
    private ShipmentType type;
    private Date date;
    private Date completedDate;
    private String fromPosition;
    private String toPosition;
    private String carrier;
    private ShipmentStatus status;
    private Long employeeId;
}
