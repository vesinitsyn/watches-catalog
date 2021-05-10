package com.vesinitsyn.watches_catalog.application.controller;

import com.vesinitsyn.watches_catalog.domain.logic.sum.basket.validation.exception.WatchesValidationException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ControllerAdviceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(WatchesValidationException.class)
    protected ResponseEntity<ApiError> handleWatchesValidationException(WatchesValidationException e) {
        return getApiErrorResponseEntity(e, NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiError> handleException(Exception e) {
        return getApiErrorResponseEntity(e, INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ApiError> getApiErrorResponseEntity(Exception e,
                                                               HttpStatus internalServerError) {
        return new ResponseEntity<>(new ApiError(e.getMessage()), internalServerError);
    }

    @Getter
    @RequiredArgsConstructor
    private static class ApiError {
        private final String message;
    }
}
