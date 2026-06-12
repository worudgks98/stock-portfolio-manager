package com.portfolio.stockmanager.controller;

import com.portfolio.stockmanager.dto.DashboardResponse;
import com.portfolio.stockmanager.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DashboardApiController {

    private final DashboardService dashboardService;

    @GetMapping("/api/dashboard")
    public DashboardResponse Dashboard(){
        return dashboardService.getDashboard();
    }
}
