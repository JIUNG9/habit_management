package com.module.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AdminHandleUserApplicationDto {

    @JsonProperty("groupName")
    private String groupName;

    @JsonProperty("userNickName")
    private String userNickName;

    @JsonProperty("adminNickName")
    private String adminNickName;

    @JsonProperty("Authorization")
    private Permit permit;

}