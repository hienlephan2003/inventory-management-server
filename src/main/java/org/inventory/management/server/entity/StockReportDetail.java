package org.inventory.management.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StockReportDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;
    @ManyToOne
    @JoinColumn(name = "stock_report_id")
    private StockReport stockReport;
    private Integer inboundQuantity;
    private Integer outboundQuantity;
    private Integer stockQuantity;
    private BigDecimal inboundPrice;
    private BigDecimal outboundPrice;
    private Date createdDate;
    private Integer needInboundQuantity;
}
