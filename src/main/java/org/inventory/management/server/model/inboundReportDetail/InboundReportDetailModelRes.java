package org.inventory.management.server.model.inboundReportDetail;

import jakarta.persistence.*;
import lombok.Data;
import org.inventory.management.server.entity.InboundReport;
import org.inventory.management.server.entity.Product;
import org.inventory.management.server.model.product.ProductModelRes;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class InboundReportDetailModelRes {
    private Long id;
    private int quantity;
    private Date expirationDate;
    private BigDecimal totalPrice;
    private BigDecimal unitPrice;
    private Date manufactoringDate;
    private int stockQuantity;
    private ProductModelRes product;
}
