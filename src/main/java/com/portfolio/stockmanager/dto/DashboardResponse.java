package com.portfolio.stockmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DashboardResponse {

    private Long totalInvestment;

    private Long totalCurrentValue;

    private Long profit;

    private Double profitRate;

}
