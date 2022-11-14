package com.module.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApplicationGroupDto {
    @JsonProperty("groupName")
    private String groupName;

    @JsonProperty("adminNickName")
    private String adminNickName;

    @JsonProperty("userNickName")
    private String userNickName;

}
