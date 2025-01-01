package org.inventory.management.server.model.outboundReportDetail;

import jakarta.persistence.*;
import lombok.Data;
import org.inventory.management.server.entity.OutboundReport;
import org.inventory.management.server.entity.Product;
import org.inventory.management.server.model.outboundLineItem.OutboundLineItemModelRes;
import org.inventory.management.server.model.product.ProductModelRes;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OutboundReportDetailModelRes {
    private Long id;
    private int quantity;
    private BigDecimal totalPrice;
    private BigDecimal unitPrice;
    private ProductModelRes product;
    private boolean isExpired;
    private List<OutboundLineItemModelRes> inbounds;
}
