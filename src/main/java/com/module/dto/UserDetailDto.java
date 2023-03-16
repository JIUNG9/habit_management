package com.module.dto;

import com.module.type.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetailDto {

    String email;
    String name;
    Role role;
}
