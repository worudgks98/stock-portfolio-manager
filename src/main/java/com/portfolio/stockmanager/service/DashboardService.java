package com.portfolio.stockmanager.service;

import com.portfolio.stockmanager.dto.DashboardResponse;
import com.portfolio.stockmanager.entity.Stock;
import com.portfolio.stockmanager.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final StockRepository stockRepository;

    public DashboardResponse getDashboard(Long memberid){

        List<Stock> stocks = stockRepository.findByMemberId(memberid);

        long totalInvestment = 0;
        long totalcurrentValue = 0;

        for(Stock stock : stocks){
            totalInvestment +=
                    (long) stock.getQuantity()
                    *stock.getBuyPrice();

            totalcurrentValue +=
                    (long) stock.getQuantity()
                    *stock.getCurrentPrice();
        }

        long profit = totalcurrentValue - totalInvestment;

        double profitRate = 0.0;

        if(totalInvestment !=0){
            profitRate = (double) profit/totalInvestment*100;
        }
        return new DashboardResponse(totalInvestment,totalcurrentValue,profit,profitRate);
    }
}
