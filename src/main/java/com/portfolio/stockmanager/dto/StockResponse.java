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

    private Long investment;
    private Long currentValue;
    private Long profit;
    private Double profitRate;

    public StockResponse(Stock stock){
        this.id = stock.getId();
        this.stockName = stock.getStockName();
        this.quantity = stock.getQuantity();
        this.buyPrice = stock.getBuyPrice();
        this.currentPrice = stock.getCurrentPrice();

        this.investment = (long)stock.getQuantity()*stock.getBuyPrice();
        this.currentValue = (long)stock.getQuantity()*stock.getCurrentPrice();
        this.profit = currentValue - investment;

        if(investment != 0){
            this.profitRate = (double)profit/investment*100;
        }else{
            this.profitRate = 0.0;
        }

    }
}
