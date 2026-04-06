package com.galaxybck.exception;

import com.galaxybck.exception.BusinessException;
import com.galaxybck.model.dto.ErrorResponse;
import com.galaxybck.model.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageService messageService; // 👈 injeta o MessageService
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(BusinessException ex) {
        logger.warn("Business exception: key={} args={}", ex.getMessageKey(), ex.getArgs());

        String message = messageService.get(ex.getMessageKey(), ex.getArgs()); // 👈 resolve aqui

        return ResponseEntity
                .status(ex.getStatus())
                .body(ErrorResponse.of(ex.getStatus(), message)); // 👈 passa a mensagem resolvida
    }
}