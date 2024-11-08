package org.inventory.management.server.model.inboundReport;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InboundReportModelRes {
    private String name;
    private BigDecimal price;
    private String description;
    private String imageUrl;
}
