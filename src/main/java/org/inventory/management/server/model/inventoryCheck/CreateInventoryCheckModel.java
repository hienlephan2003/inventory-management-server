package org.inventory.management.server.model.inventoryCheck;

import lombok.Data;
import org.inventory.management.server.model.inventoryCheck.CreateInventoryCheckModel;
import org.inventory.management.server.model.inventoryCheckDetail.CreateInventoryCheckDetailModel;

import java.util.Date;
import java.util.List;

@Data
public class CreateInventoryCheckModel {
    private Date date;
    private String name;
    private List<CreateInventoryCheckDetailModel> items;
}
