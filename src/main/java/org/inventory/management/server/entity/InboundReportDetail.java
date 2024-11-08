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
public class InboundReportDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "inbound_report_id")
    private InboundReport inboundReport;
    private int quantity;
    private BigDecimal totalPrice;
    private BigDecimal unitPrice;
    private Date manufactoringDate;
    private Date expirationDate;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
