package com.module.group.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.module.group.Group;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SortByGroupUserNumberDto {
    Long numberOfUserInGroup;
    Group group;
}
