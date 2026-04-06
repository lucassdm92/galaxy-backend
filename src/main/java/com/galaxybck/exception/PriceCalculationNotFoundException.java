package com.galaxybck.exception;

import org.springframework.http.HttpStatus;

public class PriceCalculationNotFoundException extends BusinessException {
    public PriceCalculationNotFoundException(Object... args) {
        super("price.calculation.not.found", HttpStatus.NOT_FOUND, args);
    }
}
