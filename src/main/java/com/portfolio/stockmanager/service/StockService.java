package com.portfolio.stockmanager.service;

import com.portfolio.stockmanager.dto.StockResponse;
import com.portfolio.stockmanager.dto.StockSaveRequest;
import com.portfolio.stockmanager.entity.Member;
import com.portfolio.stockmanager.entity.Stock;
import com.portfolio.stockmanager.repository.MemberRepository;
import com.portfolio.stockmanager.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final MemberRepository memberRepository;
    private final Map<String, String> tickerMap =
            new HashMap<>();

    {
        tickerMap.put("Apple", "AAPL");
        tickerMap.put("Nvidia", "NVDA");
        tickerMap.put("Tesla", "TSLA");
        tickerMap.put("Microsoft", "MSFT");

        tickerMap.put("삼성전자", "005930.KS");
        tickerMap.put("SK하이닉스", "000660.KS");
        tickerMap.put("NAVER", "035420.KS");
        tickerMap.put("카카오", "035720.KS");
    }

    public Long save(Long memberId,StockSaveRequest request){

        System.out.println("stockName = " + request.getStockName());
        System.out.println("ticker =" + request.getTicker());
        System.out.println("quantity = " + request.getQuantity());
        System.out.println("buyPrice = " + request.getBuyPrice());
        System.out.println("currentPrice = " + request.getCurrentPrice());

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        Stock stock = Stock.builder()
                .stockName(request.getStockName())
                .ticker(request.getTicker())
                .quantity(request.getQuantity())
                .buyPrice(request.getBuyPrice())
                .currentPrice(request.getCurrentPrice())
                .member(member)
                .build();

        return stockRepository.save(stock).getId();
    }

    public List<StockResponse> findAll(Long memberId){

        return stockRepository.findByMemberId(memberId)
                .stream()
                .map(StockResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long update(Long id,StockSaveRequest request){

        Stock stock = stockRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("종목이 없습니다."));

        stock.update(request.getStockName(),
                request.getTicker(),
                request.getQuantity(),
                request.getBuyPrice(),
                request.getCurrentPrice()
        );

        return stock.getId();
    }

    @Transactional
    public void delete(Long id){

        Stock stock = stockRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("종목이 없습니다."));

        stockRepository.delete(stock);
    }

    public List<StockResponse> search(
            Long memberId,
            String keyword
    ) {

        return stockRepository
                .findByMemberId(memberId)
                .stream()
                .filter(stock ->
                        stock.getStockName()
                                .contains(keyword))
                .map(StockResponse::new)
                .toList();
    }

    public List<StockResponse> sortByProfit(){

        return stockRepository.findAll()
                .stream()
                .map(StockResponse::new)
                .sorted(
                        Comparator.comparing(
                                StockResponse::getProfitRate
                        ).reversed()
                )
                .toList();
    }

    public Page<StockResponse> findPage(Pageable pageable){

        return stockRepository
                .findAll(pageable)
                .map(StockResponse::new);
    }

    public List<StockResponse> sortByProfitRate(Long memberId) {

        return stockRepository.findByMemberId(memberId)
                .stream()
                .map(StockResponse::new)
                .sorted(
                        (a, b) ->
                                Double.compare(
                                        b.getProfitRate(),
                                        a.getProfitRate()
                                )
                )
                .toList();
    }

    public Double getPrice(String ticker) {

        try {

            String url =
                    "https://query1.finance.yahoo.com/v8/finance/chart/"
                            + ticker;

            RestTemplate restTemplate =
                    new RestTemplate();

            HttpHeaders headers =
                    new HttpHeaders();

            headers.set(
                    "User-Agent",
                    "Mozilla/5.0"
            );

            HttpEntity<String> entity =
                    new HttpEntity<>(headers);

            ResponseEntity<String> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            entity,
                            String.class
                    );

            String json =
                    response.getBody();

            System.out.println(json);

            ObjectMapper mapper =
                    new ObjectMapper();

            JsonNode root =
                    mapper.readTree(json);

            return root
                    .path("chart")
                    .path("result")
                    .get(0)
                    .path("meta")
                    .path("regularMarketPrice")
                    .asDouble();

        } catch (Exception e) {

            System.out.println(
                    "가격 조회 실패 : " + ticker
            );
            e.printStackTrace();

            return null;
        }
    }
    @Transactional
    public void refreshPrices() {

        List<Stock> stocks =
                stockRepository.findAll();

        for (Stock stock : stocks) {

            System.out.println(
                    "ticker = " + stock.getTicker()
            );

            Double price =
                    getPrice(stock.getTicker());

            if(price != null){
                stock.updateCurrentPrice(
                        price.intValue()
                );
            }
        }
    }

    public String findTicker(String stockName) {

        return tickerMap.get(stockName);
    }

}
