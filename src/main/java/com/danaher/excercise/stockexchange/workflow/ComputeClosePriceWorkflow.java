package com.danaher.excercise.stockexchange.workflow;

import com.danaher.excercise.stockexchange.model.ClosePriceRecord;
import com.danaher.excercise.stockexchange.model.InternalDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


@Component
public class ComputeClosePriceWorkflow implements Function<InternalDataModel, Flux<InternalDataModel>> {

    private static final Logger logger = LoggerFactory.getLogger(ComputeClosePriceWorkflow.class);

    private final String CLOSE_KEY_NAME = "4. close";

    @Override
    public Flux<InternalDataModel> apply(InternalDataModel internalDataModel) {
        return Flux.just(internalDataModel)
                .doOnNext(this::computeClosedPrice);
    }

    private void computeClosedPrice(InternalDataModel internalDataModel) {
        internalDataModel.closePriceRecords = internalDataModel.stockExchangeResponse.timeSeries
                .entrySet().stream()
                .filter(entry -> isDateInRange(internalDataModel, entry.getKey()))
                .map(entry -> new ClosePriceRecord(entry.getKey(), entry.getValue().get(CLOSE_KEY_NAME)))
                .collect(Collectors.toList());

        logger.info("Computed Closed Price: {}", internalDataModel.closePriceRecords);

    }

    private boolean isDateInRange(InternalDataModel internalDataModel, String date) {
        LocalDate entryDate = LocalDate.parse(date);
        // Both StartDate & End date are inclusive
        return entryDate.isAfter(internalDataModel.startDate.minusDays(1)) &&
                entryDate.isBefore(internalDataModel.endDate.plusDays(1));
    }

}
