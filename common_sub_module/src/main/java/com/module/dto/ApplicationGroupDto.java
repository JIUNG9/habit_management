package com.module.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ApplicationGroupDto {
    @JsonProperty("groupName")
    private String groupName;

    @JsonProperty("adminNickName")
    private String adminNickName;

    @JsonProperty("userNickName")
    private String userNickName;

    @Override
    public String toString(){
        return "groupName: " + groupName +
                "adminNickName: " + adminNickName +
                "userNickName: " + userNickName;
    }
}
