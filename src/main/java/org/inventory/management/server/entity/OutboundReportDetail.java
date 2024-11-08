package org.inventory.management.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OutboundReportDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "outbound_report_id")
    private OutboundReport outboundReport;
    private int quantity;
    private BigDecimal totalPrice;
    private BigDecimal unitPrice;
    private boolean isExpired;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
