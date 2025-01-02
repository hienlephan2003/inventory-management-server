package org.inventory.management.server.model.inventoryCheckDetail;

import jakarta.persistence.*;
import lombok.Data;
import org.inventory.management.server.entity.Product;
import org.inventory.management.server.entity.StockReport;

import java.math.BigDecimal;
import java.util.Date;


@Data
public class CreateInventoryCheckDetailModel {
    private Long productId;
    private Integer loss; // Hao hụt
}
