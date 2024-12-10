package org.inventory.management.server.model.inboundReportDetail;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@Data
public class UpdateInboundReportDetailModel {
    private Long id;
    private int quantity;
    private BigDecimal unitPrice;
    private Date manufactoringDate;
    private Date expirationDate;
}
