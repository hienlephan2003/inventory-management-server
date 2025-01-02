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
public class OutboundReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private Integer quantity;
    private BigDecimal totalPrice;
    @ManyToOne
    @JoinColumn(name = "shipment_id")
    private Shipment shipment;
    @OneToMany(mappedBy = "outboundReport", cascade = CascadeType.ALL)
    private List<OutboundReportDetail> items;
    private Boolean isExpired;
}
