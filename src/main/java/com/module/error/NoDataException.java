package com.module.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class NoDataException {

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> NoDataFromDb(IllegalArgumentException ex) {
        return ApiError.buildApiError(ApiError.builder().message(UserErrorCode.INVALID_PARAMETER.getMessage()).timestamp(LocalDateTime.now()).status(HttpStatus.BAD_REQUEST).message("Can't get the data form the database").build());
    }
    }
