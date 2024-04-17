package com.danaher.excercise.stockexchange.controller;

import com.danaher.excercise.stockexchange.exceptions.InvalidArgsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidArgsException.class)
    public ResponseEntity<?> handleInvalidArgsException(InvalidArgsException iaEx) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(iaEx.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleISE(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}
