package com.lazarin.sample.wiremocktutorial.exception;

public class StockNotFoundException extends RuntimeException{

    public StockNotFoundException(String stockCode){
        super(String.format("Stock with code %s not found!", stockCode));
    }
}
