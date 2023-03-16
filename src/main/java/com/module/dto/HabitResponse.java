package com.module.dto;


import com.module.entity.Habit;

import java.util.List;

public class HabitResponse {
    private List<Habit> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
