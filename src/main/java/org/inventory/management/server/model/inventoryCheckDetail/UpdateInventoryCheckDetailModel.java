package org.inventory.management.server.model.inventoryCheckDetail;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@Data
public class UpdateInventoryCheckDetailModel {
    private Long id;
    private BigDecimal unitPrice;
    private Long productId;
    private Integer loss; // Hao hụt
    private Date createdDate; // Ngày kiểm kê
}
