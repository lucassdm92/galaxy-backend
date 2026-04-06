package com.galaxybck.exception;

import org.springframework.http.HttpStatus;

public class DeliveryNotFoundException extends BusinessException {
    public DeliveryNotFoundException(Object... args) {
        super("delivery.not.found", HttpStatus.NOT_FOUND, args);
    }
}
