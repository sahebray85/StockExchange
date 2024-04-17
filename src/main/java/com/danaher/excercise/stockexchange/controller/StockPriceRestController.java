package com.danaher.excercise.stockexchange.controller;


import com.danaher.excercise.stockexchange.model.ClosePriceRecord;
import com.danaher.excercise.stockexchange.model.InternalDataModel;
import com.danaher.excercise.stockexchange.service.StockPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class StockPriceRestController {

    @Autowired
    private StockPriceService service;

    @GetMapping("/price")
    public ResponseEntity<List<ClosePriceRecord>> fetch_stock_price(WebRequest webRequest) {
        InternalDataModel model = new InternalDataModel();
        model.webRequest = webRequest;
        service.fetchStockPrice(model).log().blockFirst();
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(model.closePriceRecords);
    }
}
