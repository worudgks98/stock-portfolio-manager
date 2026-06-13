package com.portfolio.stockmanager.controller;

import com.portfolio.stockmanager.dto.AllocationResponse;
import com.portfolio.stockmanager.service.ChartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChartApiController {

    private final ChartService chartService;

    @GetMapping("api/chart/allocation/{memberId}")
    public List<AllocationResponse> allocation(@PathVariable Long memberId){
        return chartService.getAllocation(memberId);
    }
}
