package com.lazarin.sample.wiremocktutorial.resource;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FinancialFactsResource {
    private Long id;
    private String stockCode;
    private String companyName;
    private Double currentPrice;
    private Double previousClosePrice;
    private Double yield;
    private Float priceToEarningsRatio;

}
