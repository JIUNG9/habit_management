package com.module.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.module.validator.GroupNameDuplicationCheck;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateGroupDto {
    @GroupNameDuplicationCheck
    @JsonProperty("groupName")
    private String groupName;

    @JsonProperty("nickName")
    private String nickName;

    @JsonProperty("groupType")
    private GroupType groupType;



}
