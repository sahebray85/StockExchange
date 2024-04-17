package com.danaher.excercise.stockexchange.service;

import com.danaher.excercise.stockexchange.model.InternalDataModel;
import com.danaher.excercise.stockexchange.workflow.ComputeClosePriceWorkflow;
import com.danaher.excercise.stockexchange.workflow.RequestValidatorWorkflow;
import com.danaher.excercise.stockexchange.workflow.StockPriceReaderWorkflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class StockPriceService {

    @Autowired
    private RequestValidatorWorkflow requestValidatorWorkflow;

    @Autowired
    private StockPriceReaderWorkflow stockPriceReaderWorkflow;

    @Autowired
    private ComputeClosePriceWorkflow computeClosePriceWorkflow;

    public Flux<InternalDataModel> fetchStockPrice(InternalDataModel dataModel) {
        return Flux.just(dataModel)
                .flatMap(requestValidatorWorkflow)
                .flatMap(stockPriceReaderWorkflow)
                .flatMap(computeClosePriceWorkflow);
    }

}
