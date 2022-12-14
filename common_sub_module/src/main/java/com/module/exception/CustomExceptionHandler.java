package com.module.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomExceptionHandler {


    @Order(Ordered.HIGHEST_PRECEDENCE+5)
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> catchRuntimeException(Exception ex) {
        return ApiError.buildApiError(
                ApiError.
                        builder().
                        message(ex.getMessage()).
                        timestamp(LocalDateTime.now()).
                        status(HttpStatus.BAD_REQUEST).
                        message(ex.getMessage()).
                        build());
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    class UserValidationException {

        @ExceptionHandler(MethodArgumentNotValidException.class)
        protected ResponseEntity<Object> ValidationError(MethodArgumentNotValidException ex) {

            List<ApiSubError> subErrors = ex.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(x -> ApiValidationError.builder().message(x.getDefaultMessage()).rejectedValue(x.getRejectedValue()).field(x.getField()).object(x.getObjectName()).build())
                    .collect(Collectors.toList());

            return new ApiError().buildApiError(
                    ApiError.
                            builder().
                            timestamp(LocalDateTime.now()).
                            status(HttpStatus.BAD_REQUEST).subErrors(subErrors).build());

        }
}

    @Order(Ordered.HIGHEST_PRECEDENCE + 2)
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> IllegalArgumentException(IllegalArgumentException ex) {
        return ApiError.buildApiError(
                ApiError.
                        builder().
                        timestamp(LocalDateTime.now()).
                        status(HttpStatus.BAD_REQUEST).
                        message(ex.getMessage()).
                        build());
    }

    @Order(Ordered.HIGHEST_PRECEDENCE + 3)
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> EntityNotFoundException(EntityNotFoundException ex) {
        return ApiError.buildApiError(
                ApiError.
                        builder().
                        timestamp(LocalDateTime.now()).
                        status(HttpStatus.NOT_FOUND).
                        message(ex.getMessage()).
                        build());
    }

    @Order(Ordered.HIGHEST_PRECEDENCE + 4)
    @ExceptionHandler(EntityExistsException.class)
    protected ResponseEntity<Object> EntityExistsException(EntityExistsException ex) {
        return ApiError.buildApiError(
                ApiError.
                        builder().
                        timestamp(LocalDateTime.now()).
                        status(HttpStatus.UNPROCESSABLE_ENTITY).
                        message(ex.getMessage()).
                        build());
    }

    //ConstraintViolationException
    @Order(Ordered.HIGHEST_PRECEDENCE + 4)
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> SqlDuplicationCheck(DataIntegrityViolationException ex) {
        return ApiError.buildApiError(
                ApiError.
                        builder().
                        timestamp(LocalDateTime.now()).
                        status(HttpStatus.UNPROCESSABLE_ENTITY).
                        message("there is same input data in the table check the nick name or group name").
                        build());
    }



    @Order(Ordered.HIGHEST_PRECEDENCE + 5)
    @ExceptionHandler(SQLException.class)
    protected ResponseEntity<Object> SqlExceptionHandler(SQLException ex) {
        return ApiError.buildApiError(
                ApiError.
                        builder().
                        timestamp(LocalDateTime.now()).
                        status(HttpStatus.UNPROCESSABLE_ENTITY).
                        message(ex.getMessage()).
                        build());
    }

}