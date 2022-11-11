package com.module.group;

import com.module.validator.EmailDuplicate;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupDto {

    @Email
    @NotNull
    private String email;
    @NotNull
    @Length(min = 2, max = 10, message = "2글자 이상 11글자 미만이여야합니다")
    private String name;
}
