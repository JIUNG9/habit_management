package com.module.security.provider;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;


//no need to authenticate
public class GroupAnonymousAuthenticationProvider implements GroupProvider {
    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        return authentication;
    }


    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}

