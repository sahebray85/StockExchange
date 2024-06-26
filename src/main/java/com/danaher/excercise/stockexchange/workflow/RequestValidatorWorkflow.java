package com.danaher.excercise.stockexchange.workflow;

import com.danaher.excercise.stockexchange.exceptions.InvalidArgsException;
import com.danaher.excercise.stockexchange.model.InternalDataModel;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

@Component
public class RequestValidatorWorkflow implements Function<InternalDataModel, Flux<InternalDataModel>> {

    private static final Logger logger = LoggerFactory.getLogger(RequestValidatorWorkflow.class);

    private final ImmutableMap<String, Supplier<String>> validators = ImmutableMap.<String, Supplier<String>>builder()
            .put("from", () -> "Request Should have from parameter")
            .put("to", () -> "Request Should have to parameter")
            .put("ticker", () -> "Request Should have ticker parameter")
            .build();

    @Value("${supported.country.list}")
    private List<String> supportedTickers;

    @Value("${max.days.duration}")
    private String maxDaysDuration;

    @Override
    public Flux<InternalDataModel> apply(InternalDataModel internalDataModel) {
        return Flux.just(internalDataModel)
                .doOnNext(this::validate);
    }

    public void validate(InternalDataModel internalDataModel) {
        WebRequest webRequest = internalDataModel.webRequest;

        for (String key : validators.keySet()) {
            try {
                webRequest.getParameter(key);
            } catch (NullPointerException ex) {
                String msg = Objects.requireNonNull(validators.get(key)).get();
                logger.error(msg);
                throw new InvalidArgsException(msg);
            }
        }

        try {
            internalDataModel.startDate = LocalDate.parse(Objects.requireNonNull(webRequest.getParameter("from")));
        } catch (DateTimeParseException ex) {
            String msg = String.format("Invalid start_data: [%s], Should be of format YYYY-MM--DD", webRequest.getParameter("from"));
            logger.error(msg);
            throw new InvalidArgsException(msg);
        }

        try {
            internalDataModel.endDate = LocalDate.parse(Objects.requireNonNull(webRequest.getParameter("to")));
        } catch (DateTimeParseException ex) {
            String msg = String.format("Invalid end_data [%s], Should be of format YYYY-MM--DD", webRequest.getParameter("to"));
            logger.error(msg);
            throw new InvalidArgsException(msg);
        }

        if (internalDataModel.startDate.isAfter(internalDataModel.endDate)) {
            String msg = "start_date shouldn't be later than end_date";
            logger.error(msg);
            throw new InvalidArgsException(msg);
        }

        if (internalDataModel.startDate.plusDays(Integer.parseInt(maxDaysDuration)).isBefore(internalDataModel.endDate)) {
            String msg = String.format("Max duration between start and end date is %s days", maxDaysDuration);
            logger.error(msg);
            throw new InvalidArgsException(msg);
        }

        // Validate ticker
        if (!supportedTickers.contains(webRequest.getParameter("ticker"))) {
            String msg = "Unsupported ticker: must of one of: " + supportedTickers;
            logger.error(msg);
            throw new InvalidArgsException(msg);
        } else {
            internalDataModel.tickerSymbol = webRequest.getParameter("ticker");
        }

    }
}
