package com.module.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.module.jwt.JwtUtil;
import com.module.security.CookieUtil;
import com.module.user.MemberForm;
import com.module.user.User;
import com.module.user.UserServiceImpl;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
public class JwtAuthorizationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userDetailsService;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserServiceImpl userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        setFilterProcessesUrl("/api/login");
        super.setAuthenticationManager(this.authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException
    {
        log.info("USERNAMEPASSWORD_FILTER");
        ObjectMapper om = new ObjectMapper();
        MemberForm memberLogin = null;

        try {
            memberLogin = om.readValue(request.getInputStream(), MemberForm.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("member : {}", memberLogin);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(memberLogin.getEmail(), memberLogin.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);


        request.setAttribute("email",memberLogin.getEmail());

        return authentication;
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                         Authentication authResult) throws IOException, ServletException {

        String jwtToken = jwtUtil.generateToken((String) request.getAttribute("email"));

        Cookie cookie = CookieUtil.create("jwtToken", jwtToken, -1, "localhost");
        response.addCookie(cookie);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed)
            throws IOException, ServletException {

        setAuthenticationFailureHandler(new CustomAuthenticationFailureHandler());
        getFailureHandler().onAuthenticationFailure(request,response,failed);
    }
}


