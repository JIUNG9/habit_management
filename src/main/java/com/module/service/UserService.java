package com.module.service;

import com.module.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    com.module.entity.User signUp(com.module.entity.User user);
    boolean checkEmailIsDuplicated(String email);
    void deleteUser(String email);
    boolean login(String email,String password);
    com.module.entity.User findUserByEmail(String email);
    com.module.entity.User updateUser(com.module.entity.User user);
    List<User> getAllUser();

}
