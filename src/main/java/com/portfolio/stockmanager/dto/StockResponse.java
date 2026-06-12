package com.portfolio.stockmanager.dto;

import com.portfolio.stockmanager.entity.Stock;
import lombok.Getter;

@Getter
public class StockResponse {

    private Long id;
    private String stockName;
    private Integer quantity;
    private Integer buyPrice;
    private Integer currentPrice;

    public StockResponse(Stock stock){
        this.id = stock.getId();
        this.stockName = stock.getStockName();
        this.quantity = stock.getQuantity();
        this.buyPrice = stock.getBuyPrice();
        this.currentPrice = stock.getCurrentPrice();
    }
}
