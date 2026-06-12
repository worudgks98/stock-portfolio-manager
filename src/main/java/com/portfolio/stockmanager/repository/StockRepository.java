package com.portfolio.stockmanager.repository;

import com.portfolio.stockmanager.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
