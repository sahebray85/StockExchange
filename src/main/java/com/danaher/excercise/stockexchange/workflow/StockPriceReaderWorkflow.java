package com.danaher.excercise.stockexchange.workflow;

import com.danaher.excercise.stockexchange.exceptions.InvalidArgsException;
import com.danaher.excercise.stockexchange.model.InternalDataModel;
import com.danaher.excercise.stockexchange.model.StockPriceResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.danaher.excercise.stockexchange.workflow.ConfigurationBean.ALPHAVANTAGE_URL;
import static com.danaher.excercise.stockexchange.workflow.ConfigurationBean.API_KEY_TOKEN_NAME;
import static com.danaher.excercise.stockexchange.workflow.ConfigurationBean.TICKER_SYMBOL;

@Component
public class StockPriceReaderWorkflow implements Function<InternalDataModel, Flux<InternalDataModel>> {

    private static final Logger logger = LoggerFactory.getLogger(StockPriceReaderWorkflow.class);

    private WebClient webClient;

    @Autowired
    private JsonMapper jsonMapper;

    @Override
    public Flux<InternalDataModel> apply(InternalDataModel internalDataModel) {
        return Flux.just(internalDataModel)
                .map(data -> fetchStockPrice(data.tickerSymbol))
                .map(response -> {
                    logger.info("Response: {} for {}", internalDataModel.tickerSymbol, response);
                    internalDataModel.stockExchangeResponse = response;
                    return internalDataModel;
                });
    }

    private StockPriceResponse fetchStockPrice(String tickerSymbol) {
        String filepath = String.format("classpath:static/daily_time_series/%s.json", tickerSymbol);
        logger.info("Reading: {}", filepath);

        try {
            File file = ResourceUtils.getFile(filepath);
            try (InputStream in = new FileInputStream(file)) {
                return jsonMapper.readValue(in.readAllBytes(), StockPriceResponse.class);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        throw new RuntimeException("Unable to process request");
    }
}
