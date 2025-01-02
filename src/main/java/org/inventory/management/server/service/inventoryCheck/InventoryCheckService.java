package org.inventory.management.server.service.inventoryCheck;

import org.inventory.management.server.entity.OutboundLineItem;
import org.inventory.management.server.entity.OutboundReportDetail;
import org.inventory.management.server.model.inventoryCheck.CreateInventoryCheckModel;
import org.inventory.management.server.model.inventoryCheck.InventoryCheckModelRes;
import org.inventory.management.server.model.inventoryCheck.ListDataRes;

import java.util.List;
import java.util.Map;

public interface InventoryCheckService {
    InventoryCheckModelRes getInventoryCheckById(long id);
    ListDataRes<InventoryCheckModelRes> getInventoryChecks();
    InventoryCheckModelRes createInventoryCheck(CreateInventoryCheckModel InventoryCheckModel);
    InventoryCheckModelRes deleteInventoryCheck(long id);
}
