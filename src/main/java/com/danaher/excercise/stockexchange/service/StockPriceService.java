package com.danaher.excercise.stockexchange.service;

import com.danaher.excercise.stockexchange.model.InternalDataModel;
import com.danaher.excercise.stockexchange.workflow.RequestValidatorWorkflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class StockPriceService {

    @Autowired
    private RequestValidatorWorkflow requestValidatorWorkflow;

    public Flux<InternalDataModel> calculateEmission(InternalDataModel dataModel) {
        return Flux.just(dataModel)
                .map(requestValidatorWorkflow);
    }

}
