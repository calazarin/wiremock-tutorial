package com.lazarin.sample.wiremocktutorial.exception;

public class StockServiceException extends RuntimeException{

    public StockServiceException(String message){
        super(message);
    }

    public StockServiceException(String message, Exception exception){
        super(message, exception);
    }

}
