package com.lazarin.sample.wiremocktutorial.service;

import com.lazarin.sample.wiremocktutorial.exception.StockNotFoundException;
import com.lazarin.sample.wiremocktutorial.exception.StockServiceException;
import com.lazarin.sample.wiremocktutorial.resource.FinancialFactsResource;
import com.lazarin.sample.wiremocktutorial.resource.StockResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class StocksService {

    private RestTemplate restTemplate;

    @Value("${stocks-api.base-url}")
    private String stockServiceBaseUrl;

    public StocksService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public StockResource findStockByCode(String code) {

        if(!StringUtils.hasText(code)){
            throw new StockServiceException("Stock status code is mandatory");
        }

        String getStockDetailsUrl = stockServiceBaseUrl + "/financial-facts/{code}";

        try {
            ResponseEntity<FinancialFactsResource> response
                    = restTemplate.getForEntity(getStockDetailsUrl, FinancialFactsResource.class, code);
            //let's suppose code calls an internal method to perform any business logic
            return performInternalBusinessLogic(response.getBody());
        } catch(Exception ex) {
            if(ex instanceof HttpClientErrorException &&
                    ((HttpClientErrorException) ex).getStatusCode().equals(HttpStatus.NOT_FOUND)){
                throw new StockNotFoundException(code);
            } else {
                throw new StockServiceException("An error happened to return stock details from financial " +
                        "facts", ex);
            }
        }
    }

    private StockResource performInternalBusinessLogic(FinancialFactsResource financialFactsResource){
        return StockResource.builder()
                .stockCode(financialFactsResource.getStockCode())
                .companyName(financialFactsResource.getCompanyName())
                .previousClosePrice(financialFactsResource.getPreviousClosePrice())
                .currentPrice(financialFactsResource.getCurrentPrice())
                .priceToEarningsRatio(financialFactsResource.getPriceToEarningsRatio())
                .yield(financialFactsResource.getYield())
                //let's suppose we have a business logic here to calculate it
                .oneYearTargetPrice(financialFactsResource.getCurrentPrice())
                //let's suppose we have a business logic here to calculate it
                .oneMonthTargetPrice(financialFactsResource.getCurrentPrice())
                //let's suppose we have a business logic here to calculate it
                .threeMonthsTargetPrice(financialFactsResource.getCurrentPrice())
                .build();
    }
}
