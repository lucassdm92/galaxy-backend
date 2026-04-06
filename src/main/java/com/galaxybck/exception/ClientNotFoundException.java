package com.galaxybck.exception;

import org.springframework.http.HttpStatus;

public class ClientNotFoundException extends BusinessException {
    public ClientNotFoundException(Object... args) {
        super("client.not.found", HttpStatus.NOT_FOUND, args);
    }
}
