package com.module.exception;

import lombok.*;

abstract class ApiSubError {

}

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
class ApiValidationError extends ApiSubError {

    private String message;
    private String object;
    private String field;
    private Object rejectedValue;



}

