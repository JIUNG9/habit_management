package com.module.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserErrorCode {
    INVALID_PARAMETER( "Invalid Request Data"),
    EMAIL_IS_DUPLICATED("There is already same email"),
    EMAIL_NOT_VERIFIED("Email is not verified yet"),
    LOGIN_FAIL("Login fail");
    private final String message;



}
