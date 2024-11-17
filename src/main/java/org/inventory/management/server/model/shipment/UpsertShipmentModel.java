package org.inventory.management.server.model.shipment;

import jakarta.persistence.*;
import lombok.Data;
import org.inventory.management.server.entity.Employee;
import org.inventory.management.server.model.enumeratiion.ShipmentStatus;
import org.inventory.management.server.model.enumeratiion.ShipmentType;

import java.util.Date;
@Data
public class UpsertShipmentModel {
    private ShipmentType type;
    private Date date;
    private String carrier;
    private ShipmentStatus status;
    private Long employeeId;
}
