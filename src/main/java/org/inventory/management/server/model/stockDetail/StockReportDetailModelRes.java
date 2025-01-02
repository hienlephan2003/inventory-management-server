package org.inventory.management.server.model.stockDetail;

import lombok.Data;
import org.inventory.management.server.entity.StockReport;
import org.inventory.management.server.model.product.ProductModelRes;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class StockReportDetailModelRes {
    private Long id;
    ProductModelRes product;
    private int quantity;
    private int outboundQuantity;
    private int expiredQuantity;
    private Date createdDate;
    private StockReport stockReport;
    private Integer inboundQuantity;
    private Integer stockQuantity;
    private BigDecimal inboundPrice;
    private BigDecimal outboundPrice;
    private Integer needInboundQuantity;
}
