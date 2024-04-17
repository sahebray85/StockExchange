package com.danaher.excercise.stockexchange.controller;


import com.danaher.excercise.stockexchange.exceptions.InvalidArgsException;
import com.danaher.excercise.stockexchange.model.InternalDataModel;
import com.danaher.excercise.stockexchange.service.StockPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping("/v1")
public class StockPriceRestController {

    @Autowired
    private StockPriceService service;

    @GetMapping("/price")
    public ResponseEntity<String> fetch_stock_price(WebRequest webRequest) {
        try {
            InternalDataModel model = new InternalDataModel();
            model.webRequest = webRequest;
            service.calculateEmission(model).log().blockFirst();
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(String.format("Your trip caused %.3fkg of CO2-equivalent", "Sankha"));
        } catch (InvalidArgsException iaEx) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(iaEx.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
