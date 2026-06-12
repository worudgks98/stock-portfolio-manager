package com.portfolio.stockmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AllocationResponse {

    private String stockName;

    private Long investment;

}
