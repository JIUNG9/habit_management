package com.module.group;

import com.module.error.ApiError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/group")
public class GroupController {

    private final GroupServiceImpl groupService;
    private static final Logger logger = LoggerFactory
            .getLogger(GroupController.class);


    @GetMapping("/new")
    public ResponseEntity<Object> createGroup( String name, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        String email = (String) httpServletRequest.getAttribute("email");
        logger.info("Group name : " + name + "admin : "+ email );
        try{
                groupService.createGroup(name, email);
                 logger.info("successfully group is made");
        }
        catch(IllegalArgumentException e){
            e.printStackTrace();
            return ApiError.buildApiError(ApiError.builder().timestamp(LocalDateTime.now()).message("fail to create group").status(HttpStatus.BAD_REQUEST).build());
        }
            return ResponseEntity.status(HttpStatus.OK).build();
    }

//    //소모임 조회
//    @GetMapping("/get-all-group")
//    public ResponseEntity<Object> getAllGroup() {
//
//        List<Group>
//        return groupService.getAll()
//    }





}
