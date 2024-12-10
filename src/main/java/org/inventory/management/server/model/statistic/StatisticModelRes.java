package org.inventory.management.server.model.statistic;

import lombok.Data;

@Data
public class StatisticModelRes {
    private int stockNumber;
    private int deliveringNumber;
    private int inboundNeededNumber;
    private int stockPercent;
    private int deliveringPercent;
    private int inboundNeededPercent;
}
