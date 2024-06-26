package com.danaher.excercise.stockexchange.model;

import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class InternalDataModel {

    public WebRequest webRequest;

    public LocalDate startDate;
    public LocalDate endDate;

    public String tickerSymbol;

    public StockPriceResponse stockExchangeResponse;

    public List<ClosePriceRecord> closePriceRecords;
}
