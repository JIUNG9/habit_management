package com.module.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MyPeriodResponse {
    private long myTotalPeriod;
    private String categoryName;
    private String memberName;
}
