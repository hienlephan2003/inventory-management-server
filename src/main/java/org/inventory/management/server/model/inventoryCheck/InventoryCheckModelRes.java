package org.inventory.management.server.model.inventoryCheck;

import lombok.Data;
import org.inventory.management.server.entity.Shipment;
import org.inventory.management.server.model.employee.EmployeeModelRes;
import org.inventory.management.server.model.inventoryCheckDetail.InventoryCheckDetailModelRes;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class InventoryCheckModelRes {
    private Long id;
    private List<InventoryCheckDetailModelRes> items;
    private BigDecimal totalPrice;
    private String name;
    private Date createdDate;
    private EmployeeModelRes employee;
}
