package org.inventory.management.server.model.inboundReportDetail;

import jakarta.persistence.*;
import lombok.Data;
import org.inventory.management.server.entity.InboundReport;
import org.inventory.management.server.entity.Product;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class InboundReportDetailModelRes {
    private Long id;
    private int quantity;
    private BigDecimal totalPrice;
    private BigDecimal unitPrice;
    private Date manufactoringDate;
    private Date expirationDate;
    private Product product;
}
