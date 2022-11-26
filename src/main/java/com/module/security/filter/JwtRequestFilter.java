package com.module.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.module.entity.User;
import com.module.security.jwt.JwtUtil;
import com.module.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.filter.OncePerRequestFilter;


@RequiredArgsConstructor
@AllArgsConstructor

public class JwtRequestFilter extends OncePerRequestFilter {

    private JwtUtil jwtUtils;
    private UserService userService;

    private static final String jwtTokenCookieName = "jwtToken";
    private static final String signingKey = "secret";
    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException, AuthenticationException {

        //token validation
        if (jwtUtils.validToken(httpServletRequest, jwtTokenCookieName, signingKey)) {
            //get email from token
            String email = jwtUtils.getEmailFromToken(httpServletRequest, jwtTokenCookieName);
            logger.info("email: "+ email);
            User user =userService.findUserByEmail(email);
            //make the Authentication
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getEmail(),null,user.getAuthorities());
            SecurityContextHolder.setContext(new SecurityContextImpl(usernamePasswordAuthenticationToken));
            logger.info("authenticate is success");
            httpServletRequest.setAttribute("email", email);

        } else {
            httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "token is not  valid");
        }


        filterChain.doFilter(httpServletRequest, httpServletResponse);


    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request)
            throws ServletException {
        String path = request.getRequestURI();
    if(path.equals("/api/user/save") || path.equals("/api/login"))
        {
            return true;
        }
        return false;
    }
}

