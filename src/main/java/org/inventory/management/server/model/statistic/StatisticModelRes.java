package org.inventory.management.server.model.statistic;

import lombok.Data;

@Data
public class StatisticModelRes {
    private int stockNumber;
    private int indboundNumber;
    private int inboundNeededNumber;
    private double stockPercent;
    private double inboundPercent;
    private double inboundNeededPercent;
    private String stockPercentChange;
    private String inboundPercentChange;
    private String inboundNeededPercentChange;
}
