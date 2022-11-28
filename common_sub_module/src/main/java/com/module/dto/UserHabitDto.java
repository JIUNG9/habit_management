package com.module.dto;


import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class UserHabitDto {

    private String userName;
    private String categoryName;
    private int totalAmount;
    private Timestamp startTime;

}
