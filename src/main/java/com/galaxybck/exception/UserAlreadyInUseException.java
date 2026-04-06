package com.galaxybck.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyInUseException extends BusinessException{
    public UserAlreadyInUseException(Object... args) {
        super("user.already.in.use", HttpStatus.BAD_REQUEST, args);
    }

}
