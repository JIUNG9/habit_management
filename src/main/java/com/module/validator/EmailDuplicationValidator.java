package com.module.validator;

import com.module.user.User;
import com.module.user.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.MessageFormat;
import java.util.Optional;

@AllArgsConstructor
public class EmailDuplicationValidator implements ConstraintValidator<EmailDuplicate, String> {

    private final UserService userService;


    @Override
    public void initialize(EmailDuplicate constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        boolean emailDuplicate = userService.checkEmailIsDuplicated(email).isPresent();
        if(emailDuplicate){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    MessageFormat.format("중복된 이메일입니다",email))
                    .addConstraintViolation();
        }
        return !emailDuplicate;
    }
}
