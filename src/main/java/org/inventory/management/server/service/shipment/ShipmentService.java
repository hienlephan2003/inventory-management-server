package org.inventory.management.server.service.shipment;

import org.inventory.management.server.model.query.ListQueryParam;
import org.inventory.management.server.model.shipment.ListShipmentModelRes;
import org.inventory.management.server.model.shipment.ShipmentModelRes;
import org.inventory.management.server.model.shipment.UpsertShipmentModel;

public interface ShipmentService {
    ListShipmentModelRes getShipments(ListQueryParam params);
    ShipmentModelRes updateShipment(Long shipmentId, UpsertShipmentModel shipmentModel);
}
