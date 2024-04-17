package com.danaher.excercise.stockexchange.exceptions;

public class InvalidArgsException extends RuntimeException {
    public InvalidArgsException(String msg) {
        super(msg);
    }
}
