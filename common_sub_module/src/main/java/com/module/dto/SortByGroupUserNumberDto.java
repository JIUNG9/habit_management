package com.module.dto;

import com.module.entity.Group;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SortByGroupUserNumberDto {
    Long numberOfUserInGroup;
    Group group;
}
