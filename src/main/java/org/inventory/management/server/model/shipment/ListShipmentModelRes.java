package org.inventory.management.server.model.shipment;

import lombok.Data;
import org.inventory.management.server.entity.Shipment;

import java.util.List;

@Data
public  class ListShipmentModelRes {
    private List<Shipment> shipmentList;
    private int total;
}
