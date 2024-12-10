package org.inventory.management.server.model.outboundReportDetail;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@Data
public class UpsertOutboundReportDetailModel {
    private int quantity;
    private BigDecimal unitPrice;
    private Long productId;
}
