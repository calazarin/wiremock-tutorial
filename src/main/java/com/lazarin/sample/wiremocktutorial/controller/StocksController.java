package com.lazarin.sample.wiremocktutorial.controller;

import com.lazarin.sample.wiremocktutorial.resource.StockResource;
import com.lazarin.sample.wiremocktutorial.service.StocksService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/stocks")
public class StocksController {

    private StocksService stockService;

    public StocksController(StocksService stocksService){
       this.stockService = stocksService;
    }

    @GetMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StockResource findStockDataByCode(@PathVariable String code) {
        log.info(String.format("Calling find stock data by code: %s", code));
        return stockService.findStockByCode(code);
    }

}
