package com.portfolio.stockmanager.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String stockName;

    private String ticker;

    private Integer quantity;

    private Integer buyPrice;

    private Integer currentPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void update(String stockName,String ticker,Integer quantity,Integer buyPrice,Integer currentPrice) {

        this.stockName = stockName;
        this.ticker = ticker;
        this.quantity = quantity;
        this.buyPrice = buyPrice;
        this.currentPrice = currentPrice;

    }

    public void updateCurrentPrice(Integer currentPrice){

        this.currentPrice = currentPrice;
    }
}
