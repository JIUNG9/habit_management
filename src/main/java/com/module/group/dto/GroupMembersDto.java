package com.module.group.dto;

import com.module.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupMembersDto {
    String nickName;
    Role role;
}
