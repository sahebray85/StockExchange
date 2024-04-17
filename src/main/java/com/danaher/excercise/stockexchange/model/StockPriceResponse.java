package com.danaher.excercise.stockexchange.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.util.Map;

@ToString
public class StockPriceResponse {

    @JsonProperty("Meta Data")
    public Map<String, String> metadata;

    @JsonProperty("Time Series (Daily)")
    public Map<String, Map<String, String>> timeSeries;
}
