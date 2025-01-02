package org.inventory.management.server.model.inventoryCheck;

import lombok.Data;

import java.util.Date;

@Data
public class InventoryCheckModel {
    private Date date;
    private int quantity;
//    private UpsertShipmentModel shipment;
//    private List<UpsertInventoryCheckDetailModel> items;
}
