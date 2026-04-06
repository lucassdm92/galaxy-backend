package com.galaxybck.exception;

import org.springframework.http.HttpStatus;

public class NoRiderAvalibleException extends BusinessException {
    public NoRiderAvalibleException(Object... args) {
        super("rider.no.availible", HttpStatus.NOT_FOUND, args);
    }
}
