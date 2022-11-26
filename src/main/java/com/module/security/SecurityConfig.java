package com.module.security;


import com.module.security.filter.ExceptionHandlerFilter;
import com.module.security.filter.GroupAuthorizationFilter;
import com.module.security.filter.JwtAuthorizationFilter;
import com.module.security.filter.JwtRequestFilter;
import com.module.security.jwt.JwtUtil;
import com.module.security.provider.GroupAdminAuthenticationProvider;
import com.module.security.provider.GroupAnonymousAuthenticationProvider;
import com.module.security.provider.GroupUserAuthenticationProvider;
import com.module.service.GroupService;
import com.module.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import java.util.Arrays;
import java.util.List;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;
    private final GroupService groupService;




    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }




    @Bean
    JwtUtil jwtUtil(){
        return new JwtUtil();
    }



    @Bean
    @Primary
    public AuthenticationManager userAuthManager(BCryptPasswordEncoder bCryptPasswordEncoder)
            throws Exception {
        return new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                if (userService.login(authentication.getPrincipal().toString(),authentication.getCredentials().toString())) {
                    return authentication;
                }
                throw new BadCredentialsException("user email or password is invalid");

            }
        };
    }


        @Bean
        public AuthenticationManager groupAuthManager (List < AuthenticationProvider > groupProviders) throws Exception
        {
            return new ProviderManager(groupProviders);
        }


        @Bean
        public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {

            http.
                    csrf().
                    disable().
                    httpBasic().
                    and().
                    logout().
                    disable().
                    formLogin().
                    disable().
                    authorizeRequests().
                    antMatchers("/api/user/save").permitAll().
                    antMatchers("/api/logout").permitAll().
                    antMatchers("/api/login").hasAnyRole("ADMIN", "USER").
                    antMatchers("/api/user/**/*").hasAnyRole("USER", "ADMIN").
                    antMatchers("/api/admin/**/*").hasRole("ADMIN").
                    anyRequest().hasAnyRole("USER", "ADMIN");


            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            http.addFilterBefore(new ExceptionHandlerFilter(), LogoutFilter.class);
            http.addFilterAfter(new JwtRequestFilter(jwtUtil(), userService), JwtAuthorizationFilter.class);
            http.addFilterAt(new JwtAuthorizationFilter(jwtUtil(), userAuthManager(bCryptPasswordEncoder()), userService), UsernamePasswordAuthenticationFilter.class);
            http.addFilterAfter(new GroupAuthorizationFilter(groupService, groupAuthManager(Arrays.asList(new GroupAdminAuthenticationProvider(), new GroupUserAuthenticationProvider(), new GroupAnonymousAuthenticationProvider())), userService), JwtRequestFilter.class);
            return http.build();
        }


        @Bean
        public WebSecurityCustomizer webSecurityCustomizer () {
            return (web) -> web.debug(true)
                    .ignoring()
                    .antMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/favicon.ico");
        }
    }



