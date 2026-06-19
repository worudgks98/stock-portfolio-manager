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

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final MemberRepository memberRepository;

    public Long save(Long memberId,StockSaveRequest request){

        System.out.println("stockName = " + request.getStockName());
        System.out.println("quantity = " + request.getQuantity());
        System.out.println("buyPrice = " + request.getBuyPrice());
        System.out.println("currentPrice = " + request.getCurrentPrice());

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        Stock stock = Stock.builder()
                .stockName(request.getStockName())
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
}
