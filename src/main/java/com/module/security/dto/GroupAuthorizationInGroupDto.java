package com.module.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import com.module.security.json.UserAuthorizationJsonHandler;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@JsonDeserialize(using = UserAuthorizationJsonHandler.class)
public class GroupAuthorizationInGroupDto {

    @JsonProperty("groupName")
    private String groupName;

    @JsonProperty("myNickName")
    private String myNickName;


}