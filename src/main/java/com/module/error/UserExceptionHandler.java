package com.module.error;


import com.module.user.UserService;
import com.module.user.UserServiceImpl;
import com.nimbusds.oauth2.sdk.ErrorResponse;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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









