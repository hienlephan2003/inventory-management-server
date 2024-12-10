package org.inventory.management.server.model.inboundReportDetail;
import lombok.Data;


import java.math.BigDecimal;
import java.util.Date;


@Data
public class CreateInboundReportDetailModel {
    private int quantity;
    private BigDecimal unitPrice;
    private Date manufactoringDate;
    private Date expirationDate;
    private Long productId;
}
