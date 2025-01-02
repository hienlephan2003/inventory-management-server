package org.inventory.management.server.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class InboundReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private Integer quantity;
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "shipment_id")
    private Shipment shipment;
    @OneToMany(mappedBy = "inboundReport", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<InboundReportDetail> items;
}
