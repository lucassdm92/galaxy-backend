package com.galaxybck.exception;

import org.springframework.http.HttpStatus;

public class EmailAlreadyInUseException extends BusinessException {
    public EmailAlreadyInUseException(Object... args) {
        super("email.already.in.use", HttpStatus.BAD_REQUEST, args);
    }

}
