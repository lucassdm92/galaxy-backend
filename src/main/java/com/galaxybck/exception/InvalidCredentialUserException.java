package com.galaxybck.exception;

import org.springframework.http.HttpStatus;

public class InvalidCredentialUserException extends BusinessException {
    public InvalidCredentialUserException(Object... args) {
        super("log.invalid.use", HttpStatus.BAD_REQUEST, args);
    }
}
