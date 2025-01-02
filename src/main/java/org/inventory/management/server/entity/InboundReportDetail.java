package org.inventory.management.server.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;


@Getter
@Setter
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
    private Integer quantity;
    private Integer stockQuantity;
    private BigDecimal totalPrice;
    private BigDecimal unitPrice;
    private Date manufactoringDate;
    private Date expirationDate;
    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;
    private Date createdDate;
}
