package org.inventory.management.server.model.stock;

import jakarta.persistence.*;
import lombok.Data;
import org.inventory.management.server.entity.StockReportDetail;
import org.inventory.management.server.model.stockDetail.StockReportDetailModelRes;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class StockReportModelRes {
    private Long id;
    private List<StockReportDetailModelRes> items;
    private Date startDate;
    private Date endDate;
    private String name;
    private Integer inboundQuantity;
    private Integer outboundQuantity;
    private Integer stockQuantity;
    private BigDecimal inboundPrice;
    private BigDecimal outboundPrice;
    private BigDecimal totalPrice;
    private Integer needInboundQuantity;
}
