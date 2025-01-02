package org.inventory.management.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.inventory.management.server.model.enumeratiion.CalendarType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
//stock report nay la moi thang mot lan
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StockReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date startDate;
    private Date endDate;
    private Date date;
    @OneToMany(mappedBy = "stockReport", cascade = CascadeType.ALL)
    private List<StockReportDetail> items;
    private Integer inboundQuantity;
    private Integer outboundQuantity;
    private Integer stockQuantity;
    private Integer needInboundQuantity;
    private BigDecimal inboundPrice;
    private BigDecimal outboundPrice;
    private BigDecimal totalPrice;
    private String name;
}
