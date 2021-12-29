package com.lazarin.sample.wiremocktutorial.resource;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StockResource {
    private String guid;
    private String stockCode;
    private String companyName;
    private Double currentPrice;
    private Double previousClosePrice;
    private Double yield;
    private Float priceToEarningsRatio;
    private Double oneYearTargetPrice;
    private Double oneMonthTargetPrice;
    private Double threeMonthsTargetPrice;

}
