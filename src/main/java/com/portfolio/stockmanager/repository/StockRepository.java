package com.portfolio.stockmanager.repository;

import com.portfolio.stockmanager.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {

    List<Stock> findByStockNameContaining(String keyword);

    List<Stock> findByMemberId(Long memberId);

    List<Stock> findAllByOrderByCurrentPriceDesc();
}
