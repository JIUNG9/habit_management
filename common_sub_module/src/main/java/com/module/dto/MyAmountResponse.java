package com.module.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MyAmountResponse {
    private long myTotalAmount;
    private String categoryName;
    private String memberName;
}
