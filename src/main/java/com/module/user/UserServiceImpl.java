package com.module.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;


@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public Optional<User> signUp(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return ofNullable(userRepository.save(user));
    }

    @Override
    public Optional<User> checkEmailIsDuplicated(String email) {
        return ofNullable(userRepository.getUserByEmail(email));
    }

    @Override
    public Optional<User> deleteUser(String email) {
        User user = null;
        if (userRepository.getUserByEmail(email) != null) {
            user = userRepository.getUserByEmail(email);
            userRepository.delete(user);
        }
        return ofNullable(user);
    }


    @Override
    public boolean
    login(String email, String password) {
        if (userRepository.getUserByEmail(email) != null) {
            if (bCryptPasswordEncoder.matches(password, userRepository.getUserByEmail(email).getPassword())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }


    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        if(userRepository.getUserByEmail(email)!=null){
            return userRepository.getUserByEmail(email);
        }

        throw new UsernameNotFoundException("email or password is not matched");
    }
}

