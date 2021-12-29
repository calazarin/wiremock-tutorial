package com.lazarin.sample.wiremocktutorial.resource;

public class FinancialFactsResource {
    private Long id;
    private String stockCode;
    private String companyName;
    private Double currentPrice;
    private Double previousClosePrice;
    private Double yield;
    private Float priceToEarningsRatio;

    public FinancialFactsResource() {
    }

    public FinancialFactsResource(Long id, String stockCode, String companyName, Double currentPrice, Double previousClosePrice, Double yield, Float priceToEarningsRatio) {
        this.id = id;
        this.stockCode = stockCode;
        this.companyName = companyName;
        this.currentPrice = currentPrice;
        this.previousClosePrice = previousClosePrice;
        this.yield = yield;
        this.priceToEarningsRatio = priceToEarningsRatio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Double getPreviousClosePrice() {
        return previousClosePrice;
    }

    public void setPreviousClosePrice(Double previousClosePrice) {
        this.previousClosePrice = previousClosePrice;
    }

    public Double getYield() {
        return yield;
    }

    public void setYield(Double yield) {
        this.yield = yield;
    }

    public Float getPriceToEarningsRatio() {
        return priceToEarningsRatio;
    }

    public void setPriceToEarningsRatio(Float priceToEarningsRatio) {
        this.priceToEarningsRatio = priceToEarningsRatio;
    }
}
