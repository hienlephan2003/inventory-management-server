package org.inventory.management.server.model.shipment;

import lombok.Data;
import org.inventory.management.server.entity.Product;
import org.inventory.management.server.model.enumeratiion.ShipmentStatus;
import org.inventory.management.server.model.enumeratiion.ShipmentType;

import java.util.Date;
import java.util.List;

@Data
public class UpsertShipmentModel {
    private ShipmentType type;
    private Date date;
    private String carrier;
    private ShipmentStatus status;
    private Long employeeId;
    private String fromPosition;
    private String toPosition;
    private Date completedDate;
}
