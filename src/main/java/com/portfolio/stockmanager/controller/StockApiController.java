package com.portfolio.stockmanager.controller;

import com.portfolio.stockmanager.dto.StockResponse;
import com.portfolio.stockmanager.dto.StockSaveRequest;
import com.portfolio.stockmanager.entity.Stock;
import com.portfolio.stockmanager.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/stocks")
public class StockApiController {

    private final StockService stockService;

    @PostMapping
    public Long save(@RequestBody StockSaveRequest request) {

        return stockService.save(request);
    }

    @GetMapping("/test")
    public String test() {
        return "stock api ok";
    }

    @GetMapping
    public List<StockResponse> findAll() {
        return stockService.findAll();
    }

    @PutMapping("/{id}")
    public Long update(@PathVariable Long id, @RequestBody StockSaveRequest request) {

        return stockService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {

        stockService.delete(id);

        return "삭제 완료";
    }

    @GetMapping("/search")
    public List<StockResponse> search(@RequestParam String keyword) {

        return stockService.search(keyword);
    }

    @GetMapping("/sort/profit")
    public List<StockResponse> sortByprofit(){
        return stockService.sortByProfit();
    }
}

