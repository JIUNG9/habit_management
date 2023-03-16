package com.module.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.module.type.GroupType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class CreateGroupDto {
    @JsonProperty("groupName")
    private String groupName;

    @JsonProperty("nickName")
    private String nickName;

    @NotNull
    @JsonProperty("groupType")
    private GroupType groupType;


    @Override
    public String toString(){
        return "groupName: "+ groupName  +
                "group type: "+ groupType +
                "nick name: " + nickName;
    }

}
