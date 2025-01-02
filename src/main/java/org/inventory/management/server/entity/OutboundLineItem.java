package org.inventory.management.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OutboundLineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outbound_report_detail_id")
    private OutboundReportDetail outboundReportDetail;
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inbound_report_detail_id")
    private InboundReportDetail inboundReportDetail;
}
