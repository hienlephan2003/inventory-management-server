package org.inventory.management.server.model.stockDetail;

import lombok.Data;
import org.inventory.management.server.model.product.ProductModelRes;

import java.util.Date;

@Data
public class StockReportDetailModelRes {
    private Long id;
    ProductModelRes product;
    private int quantity;
    private int outboundQuantity;
    private int expiredQuantity;
    private Date createdDate;
}
