package com.galaxybck.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundExcpetion extends BusinessException{
    public UserNotFoundExcpetion(Object... args) {
        super("user.not.found", HttpStatus.BAD_REQUEST, args);
    }
}
