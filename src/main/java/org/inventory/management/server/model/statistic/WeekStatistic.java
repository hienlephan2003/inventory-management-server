package org.inventory.management.server.model.statistic;

import lombok.Data;

import java.util.Map;

@Data
public class WeekStatistic {
    private Map<String, Integer> inbounds;
    private Map<String, Integer> outbounds;
}
