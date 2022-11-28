package com.module.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.module.entity.Group;
import com.module.entity.User;
import com.module.entity.UserInGroup;
import com.module.security.dto.GroupAuthorizationInGroupDto;
import com.module.security.provider.GroupAdminAuthenticationProvider;
import com.module.service.GroupService;
import com.module.service.UserService;

import com.module.security.provider.GroupAnonymousAuthenticationProvider;
import com.module.security.provider.GroupUserAuthenticationProvider;

import com.module.utils.http.CachedBodyHttpServletRequest;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GroupAuthorizationFilter extends UsernamePasswordAuthenticationFilter{

    private GroupService groupService;
    private final AuthenticationManager authenticationManager;
    private final UserService userServiceImpl;
    private CachedBodyHttpServletRequest cachedBodyHttpServletRequest;

    public GroupAuthorizationFilter(GroupService groupService, AuthenticationManager authenticationManager, UserService userServiceImpl){
        this.groupService=groupService;
        this.authenticationManager= authenticationManager;
        this.userServiceImpl = userServiceImpl;
        super.setAuthenticationManager(authenticationManager);
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/group/**/*"));

    }


    /*
    The logic

    1.set the requestBody all the admin function. for getting valid UserInGroup instance which has authority

    2. In UsernamePasswordAuthenticationToken principal, credentials can be null because I will use custom Authentication-
    Manager which only check authority

    3.so set a authority in loadByUser which in groupService.

     */

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        //use CachedBodyHttpServletRequest for reuse the request input stream
        cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(request);

        //need to authorize(admin or user)

        if(request.getRequestURI().contains("/api/group/admin/") || request.getRequestURI().contains("/api/group/user/")) {

            ObjectMapper om = new ObjectMapper();
            GroupAuthorizationInGroupDto dto = om.readValue(cachedBodyHttpServletRequest.getInputStream(), GroupAuthorizationInGroupDto.class);
            logger.info("dto: " + dto.toString());

                //get group by group name
                Group group = groupService.getGroupByGroupName(dto.getGroupName());
                //get user in group for comparing the input user is real admin
                UserInGroup userInGroup = groupService.getUserInGroupByUserNickNameAndGroup(dto.getMyNickName(), group);
                //get user from request(user login info)
                UserDetails getUserDetailInGroup = groupService.loadUserByUsername(userInGroup.getNickName());
                //get a user from userInGroup
                User user = (User) userServiceImpl.loadUserByUsername((String) request.getAttribute("email"));

                //compare both(input information and login information), so if anonymous user access this url, can block
                if (userInGroup.getUser().getId().intValue() == user.getId()) {
                    logger.info("input info is matched with login info");
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            request.getUserPrincipal(),
                            null,
                            getUserDetailInGroup.getAuthorities());


                    //check the group role
                    if (request.getRequestURI().contains("/api/group/admin/")) {
                        return new GroupAdminAuthenticationProvider().authenticate(authenticationToken);
                    } else if (request.getRequestURI().contains("/api/group/user/")) {
                        return new GroupUserAuthenticationProvider().authenticate(authenticationToken);
                    }
                } else {
                    throw new BadCredentialsException("you are not authorized or passed value is not match with your login information");
                }
        }

        logger.info("user is anonymous");
        List<GrantedAuthority> anonymousUser = new ArrayList<>();
        anonymousUser.add(new SimpleGrantedAuthority("GROUP_ANONYMOUS"));
        UsernamePasswordAuthenticationToken s = new UsernamePasswordAuthenticationToken("test","test", anonymousUser);
        return new GroupAnonymousAuthenticationProvider().authenticate(s);
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                         Authentication authResult) throws IOException, ServletException {

            logger.info(authResult);

            String authority =
                    authResult.
                    getAuthorities().
                    stream().
                    map(s->s.getAuthority()).
                    findFirst().
                    orElseThrow();

            request.setAttribute("authority", authority);

            chain.doFilter(cachedBodyHttpServletRequest, response);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        setAuthenticationFailureHandler(new CustomAuthenticationFailureHandler());
        getFailureHandler().onAuthenticationFailure(request, response, failed);
    }


}


