package org.inventory.management.server.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Setter
@Getter
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
    private Integer quantity;
    private BigDecimal totalPrice;
    private BigDecimal unitPrice;
    private Boolean isExpired;
    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;
    @OneToMany(mappedBy = "outboundReportDetail", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<OutboundLineItem> inbounds;
    private Date createdDate = new Date();
}
