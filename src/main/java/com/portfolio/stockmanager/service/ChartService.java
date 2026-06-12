package com.portfolio.stockmanager.service;

import com.portfolio.stockmanager.dto.AllocationResponse;
import com.portfolio.stockmanager.entity.Stock;
import com.portfolio.stockmanager.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChartService {

    private final StockRepository stockRepository;

    public List<AllocationResponse> getAllocation(){

        List<Stock> stocks = stockRepository.findAll();

        List<AllocationResponse> result = new ArrayList<>();

        for(Stock stock : stocks){
            long investment = (long)stock.getQuantity()
                    *stock.getBuyPrice();

            result.add(new AllocationResponse(stock.getStockName(),investment));
        }

        return result;

    }
}
