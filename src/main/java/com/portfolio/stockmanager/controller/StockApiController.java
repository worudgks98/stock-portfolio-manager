package com.portfolio.stockmanager.controller;

import com.portfolio.stockmanager.dto.StockResponse;
import com.portfolio.stockmanager.dto.StockSaveRequest;
import com.portfolio.stockmanager.entity.Stock;
import com.portfolio.stockmanager.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("api/stocks")
public class StockApiController {

    private final StockService stockService;

    @PostMapping("/{memberId}")
    public Long save(@PathVariable Long memberId,@RequestBody StockSaveRequest request) {

        return stockService.save(memberId,request);
    }

    @GetMapping("/test")
    public String test() {
        return "stock api ok";
    }

    @GetMapping("/member/{memberId}")
    public List<StockResponse> findAll(@PathVariable Long memberId) {
        return stockService.findAll(memberId);
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
    public List<StockResponse> search(
            @RequestParam Long memberId,
            @RequestParam String keyword
    ) {

        return stockService.search(
                memberId,
                keyword
        );
    }

    @GetMapping("/sort/profit")
    public List<StockResponse> sortByprofit(){
        return stockService.sortByProfit();
    }

    @GetMapping("/page")
    public Page<StockResponse> page(Pageable pageable) {

        return stockService.findPage(pageable);
    }
    @GetMapping("/sort/profit-rate/{memberId}")
    public List<StockResponse> sortByProfitRate(
            @PathVariable Long memberId
    ) {

        return stockService.sortByProfitRate(memberId);
    }
    @GetMapping("/price/{ticker}")
    public Double getPrice(@PathVariable String ticker){

        return stockService.getPrice(ticker);
    }
    @PostMapping("/refresh")
    public String refreshPrices() {

        stockService.refreshPrices();

        return "ok";
    }
    @GetMapping("/ticker")
    public String findTicker(
            @RequestParam String stockName
    ) {

        return stockService.findTicker(
                stockName
        );
    }

    @GetMapping("/exchange-rate")
    public Double exchangeRate() {

        return stockService.getUsdKrwRate();
    }
}

