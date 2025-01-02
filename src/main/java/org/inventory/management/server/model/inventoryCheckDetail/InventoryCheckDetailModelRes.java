package org.inventory.management.server.model.inventoryCheckDetail;

import lombok.Data;
import org.inventory.management.server.model.product.ProductModelRes;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class InventoryCheckDetailModelRes {
    private Long id;
    private BigDecimal unitPrice;
    private Integer stock; // Tồn kho
    private Integer actualQuantity; // Thực tế
    private Integer loss; // Hao hụt
    private BigDecimal lossValue; // Giá trị hao hụt
    private Date createdDate; // Ngày kiểm kê
    private ProductModelRes product;
}
