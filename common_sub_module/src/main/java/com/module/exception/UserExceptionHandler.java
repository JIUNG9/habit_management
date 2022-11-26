package com.module.exception;


import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
    class UserValidationException {

        @ExceptionHandler(MethodArgumentNotValidException.class)
        protected ResponseEntity<Object> ValidationError(MethodArgumentNotValidException ex) {

            List<ApiSubError> subErrors = ex.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(x-> ApiValidationError.builder().message(x.getDefaultMessage()).rejectedValue(x.getRejectedValue()).field(x.getField()).object(x.getObjectName()).build())
                    .collect(Collectors.toList());

           return new ApiError().buildApiError(ApiError.builder().message(UserErrorCode.INVALID_PARAMETER.getMessage()).timestamp(LocalDateTime.now()).status(HttpStatus.BAD_REQUEST).subErrors(subErrors).build());

        }


    }









