package org.inventory.management.server.model.outboundReportDetail;

import jakarta.persistence.*;
import lombok.Data;
import org.inventory.management.server.entity.OutboundReport;
import org.inventory.management.server.entity.Product;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OutboundReportDetailModelRes {
    private Long id;
    private int quantity;
    private BigDecimal totalPrice;
    private BigDecimal unitPrice;
    private Product product;
    private boolean isExpired;
}
