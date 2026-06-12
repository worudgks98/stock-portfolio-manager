package com.portfolio.stockmanager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockSaveRequest {

    private String stockName;

    private Integer quantity;

    private Integer buyPrice;

    private Integer currentPrie;
}
