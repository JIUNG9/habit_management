package com.module.group;

import com.module.error.ApiError;
import com.module.user.Role;
import com.module.user.User;
import com.module.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/group")
public class GroupController {

    private final GroupService groupService;
    private final UserService userService;
    private static final Logger logger = LoggerFactory
            .getLogger(GroupController.class);


    //create new group
    @PostMapping("/new")
    public ResponseEntity<Object> createGroup(@RequestBody CreateGroupDto createGroupDto, HttpServletRequest request) {
        String adminEmail = request.getParameter("email");
        logger.info("Group name : " + createGroupDto.getGroupName() + " nickName : " + createGroupDto.getNickName(), "group type: " + createGroupDto.getGroupType());
        try {
            groupService.createGroup(createGroupDto.getGroupName(), adminEmail, createGroupDto.getNickName() , createGroupDto.getGroupType());
            logger.info("successfully group is made");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ApiError.buildApiError(ApiError.builder().timestamp(LocalDateTime.now()).message("fail to create group").status(HttpStatus.BAD_REQUEST).build());
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //get group by type
    @GetMapping("/get-type")
    public ResponseEntity<Object> getAllGroup(@RequestParam GroupType groupType) {
        logger.info("group type is:" + groupType.name());
        List<Group> groupList = groupService.getByType(groupType);
        List<Integer> groupNumberResult = groupService.getUserNumberInGroup(groupList);
        logger.info(String.valueOf(groupNumberResult.stream().iterator().next()));
        return ResponseEntity.status(HttpStatus.OK).body(groupList.stream().map(u -> u.toString() + "the number of user: " + groupNumberResult.stream().iterator().next()).collect(Collectors.joining("\n", "{", "}")));
    }

    //find a a group by admin and group name
    @GetMapping("/get-group")
    public ResponseEntity<Object> getGroup(@RequestParam String groupName, @RequestParam String adminNickName) {
        logger.info("the group name which you want to find :" + groupName + " and admin is " + adminNickName);
        Group group = groupService.getGroupByAdminAndGroupName(adminNickName , groupName);
        List<Integer> groupNumberResult = groupService.getUserNumberInGroup(Collections.singletonList(group));
        return ResponseEntity.status(HttpStatus.OK).body("{ " + group.toString() + "the number of user: " + groupNumberResult.get(0) + "}");
    }


    //그룹 가입 신청
    @PostMapping("/apply")
    public ResponseEntity<Object> applyGroup(@RequestBody ApplicationGroupDto applicationGroupDto, HttpServletRequest request) {
        String userEmail = (String)request.getAttribute("email");
        User user = Optional.ofNullable(userService.findUserByEmail(userEmail)).orElseThrow(EntityNotFoundException::new);
        Optional.ofNullable(groupService.applyGroup(applicationGroupDto.getAdminNickName(), applicationGroupDto.getGroupName(), user, applicationGroupDto.getUserNickName())).orElseThrow(IllegalArgumentException::new);
        return ResponseEntity.status(HttpStatus.OK).body("Sign up is succeeded, admin will approve soon");
    }

    //for using loadByUser => make nickName unique

    @PostMapping("/admin/handle-application")
    public ResponseEntity<Object> applyPermissionOrDenied(@RequestBody AdminHandleUserApplicationDto dto, HttpServletRequest request) {

        //make service that get a group by groupName(group name is unique)
        //and make nick name is unique for using as a parameter at loadByUser method in groupService

        Group group = groupService.getGroupByGroupName(dto.getGroupName());
        logger.info("Group: " + group.toString());
        UserInGroup userInGroup =groupService.getUserInGroupByUserNickNameAndGroup(dto.getUserNickName(), group);
        logger.info("user in group: "+ userInGroup.toString());

        if(dto.getPermit().getPermit().equals("DENIED")){
            userInGroup.setRole(Role.ROLE_GROUP_DENIED);
        }
        else{
            userInGroup.setRole(Role.ROLE_GROUP_USER);
        }
            groupService.consentOrDenyUser(userInGroup);
        return ResponseEntity.status(HttpStatus.OK).body("User status is update pending to " + dto.getPermit());
    }


}