package com.module.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.module.group.GroupService;
import com.module.security.CookieUtil;
import com.module.user.MemberForm;
import com.module.user.User;
import com.module.user.UserService;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class GroupAuthorizationFilter extends UsernamePasswordAuthenticationFilter {

    private final GroupService groupService;
    private final UserService userService;
    private  AuthenticationManager authenticationManager;

    public GroupAuthorizationFilter(GroupService groupService, UserService userService){
        this.groupService=groupService;
        this.userService = userService;
        this.authenticationManager=this.getGroupAuthManager();
        setFilterProcessesUrl("/api/group/admin/**/*");
    }

    //get new AuthenticationManager which use loadByUser in GroupService
    private AuthenticationManager getGroupAuthManager(){
        authenticationManager = new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                if(authentication.getAuthorities().contains("ROLE_GROUP_ADMIN")){
                    authentication.setAuthenticated(true);
                }
                return authentication;
            }
        };
        return authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        //get member id and get group id
        groupService.loadUserByUsername(request.getParameter("email"));


    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                         Authentication authResult) throws IOException, ServletException {



    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) {


    }

    }


