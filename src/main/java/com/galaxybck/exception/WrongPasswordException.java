package com.galaxybck.exception;

import org.springframework.http.HttpStatus;

public class WrongPasswordException extends BusinessException {
    public WrongPasswordException(Object... args) {
        super("delivery.wrong.password", HttpStatus.BAD_REQUEST, args);
    }
}
