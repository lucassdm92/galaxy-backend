package com.galaxybck.model.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
@Slf4j

public class MessageService {

    private final MessageSource messageSource;

    public String get(String key, Object... args) {
        try {
            String message = messageSource.getMessage(key, args, Locale.ENGLISH);

            log.info("Resolved message: key={} message={}", key, message);
            return message;
        } catch (NoSuchMessageException e) {
            log.error("Message key not found: {}", key);
            return key; // retorna a chave se não encontrar
        }
    }
}