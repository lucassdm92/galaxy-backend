package com.galaxybck.exception;

import org.springframework.http.HttpStatus;

public class RiderNotFoundException extends BusinessException {
    public RiderNotFoundException(Object... args) {
        super("rider.not.found", HttpStatus.NOT_FOUND, args);
    }
}
