package com.module.controller;



import com.module.dto.*;
import com.module.entity.UserInGroup;
import com.module.type.GroupType;
import com.module.entity.Group;
import com.module.service.GroupService;

import com.module.type.Permit;
import com.module.type.Role;
import com.module.security.dto.GroupAuthorizationInGroupDto;
import com.module.entity.User;
import com.module.service.UserService;
import com.module.utils.lambda.BindParameterSupplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.*;
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


    @PostMapping("/new")
    public ResponseEntity<Object> createGroup(@RequestBody @Valid CreateGroupDto createGroupDto, HttpServletRequest request) {

        String adminEmail = (String) request.getAttribute("email");
        logger.info(createGroupDto.toString());

        try {
                groupService.createGroup(
                    createGroupDto.getGroupName(),
                    adminEmail,
                    createGroupDto.getNickName(),
                    createGroupDto.getGroupType());

            logger.info("successfully group is made");

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("group is successfully made");
    }


    @GetMapping("/get-type")
    public ResponseEntity<Object> getGroupByType(@RequestParam GroupType groupType,
                                                 @RequestParam(required = false, defaultValue = "0") Integer page,
                                                 @RequestParam(required = false, defaultValue = "name") String sortBy){

        logger.info("group type is: " + groupType.name()+ "\n" +
                    "sortBy: "+ sortBy);


        List<com.module.entity.Group> groupList = groupService.sortByType(groupType, page);

        if(sortBy.matches("name")) {

            List<SortByGroupUserNumberDto> dto = groupService.sortByNameOrder(groupList,page);
            List<String> groupAdmin = groupService.getAdminNickNameByGroupList(dto.stream().map(SortByGroupUserNumberDto::getGroup).collect(Collectors.toList()));

            logger.info("sortByType list size: " + groupList.size() +"  Sort result number : "+ dto.size() + "  groupAdmin number "+ groupAdmin.size());

            List<GroupInfoDto> sortResultList = new ArrayList<>();

            for (int i = 0; i < groupList.size(); i++) {

                sortResultList.add(
                        new GroupInfoDto(
                                dto.get(i).getNumberOfUserInGroup().intValue(),
                                dto.get(i).getGroup().getName(),
                                dto.get(i).getGroup().getGroupType(),
                                groupAdmin.get(i)));
            }

            return ResponseEntity.status(HttpStatus.OK).body(sortResultList);
        } else {

            List<SortByGroupUserNumberDto> dto = groupService.sortByUserNumberInGroup(groupList, page);
            List<String> groupAdmin = groupService.getAdminNickNameByGroupList(dto.stream().map(SortByGroupUserNumberDto::getGroup).collect(Collectors.toList()));

            List<GroupInfoDto> sortResultList = new ArrayList<>();

            for(int i=0; i< dto.size(); i ++){

                sortResultList.add(
                        new GroupInfoDto(
                                dto.get(i).getNumberOfUserInGroup().intValue(),
                                dto.get(i).getGroup().getName(),
                                dto.get(i).getGroup().getGroupType(),
                                groupAdmin.get(i)));
            }

            return ResponseEntity.status(HttpStatus.OK).body(sortResultList);

        }
        }



    /*

    * this class return one result by group name and admin name.
    * */
    @GetMapping("/get-group")
    public ResponseEntity<Object> getGroup(@RequestParam String groupName, @RequestParam String adminNickName) {
        logger.info("the group name which you want to find :" + groupName + " and admin is " + adminNickName);

        UserInGroup groupInUser = groupService.getUserInGroupByNickName(adminNickName);
        com.module.entity.Group group = groupService.getGroupByAdminAndGroupName(groupInUser.getGroup().getAdmin(), groupName);
        List<Integer> groupNumberResult = groupService.getUserNumberInGroup(Collections.singletonList(group));

        GroupInfoDto groupInfoDto =
                new GroupInfoDto(
                groupNumberResult.get(0).intValue(),
                group.getName(),
                group.getGroupType(),
                groupInUser.getNickName());

        return ResponseEntity.status(HttpStatus.OK).body(groupInfoDto);
    }

    /*
    *
    * this method is returning the one user with the request group name
    * if there isn't user with request group name wil be failed
     */
    @PostMapping("/user/get-my-role")
    public ResponseEntity<Object> getMyRole(@RequestBody GroupAuthorizationInGroupDto dto,HttpServletRequest request) {

      String authority =(String)request.getAttribute("authority");

        return ResponseEntity.status(HttpStatus.OK).body(authority);
    }

    @PostMapping("/user/get-group-members")
    public ResponseEntity<Object> getMembersInGroup(@RequestBody GroupAuthorizationInGroupDto dto) {

        logger.info("the group name which you want to find :" + dto.getGroupName() + " and user is " + dto.getMyNickName());
        List<UserInGroup> userInGroups =groupService.getMembersInGroup(dto.getGroupName());

        List<GroupMembersDto> groupMembersDtoList = new ArrayList<>();

        userInGroups.
                stream().
                map(s->groupMembersDtoList.add(new GroupMembersDto(s.getNickName(),s.getRole()))).
                collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(groupMembersDtoList);
    }



    @PostMapping("/apply")
    public ResponseEntity<Object> applyGroup(@RequestBody @Valid ApplicationGroupDto applicationGroupDto,
                                             HttpServletRequest request) {

        logger.info(applicationGroupDto.toString());


        //check the input group with adminNickName is exist first
        String adminNickName = applicationGroupDto.getAdminNickName();
        String aminEmail = groupService.getAdminEmailByAdminNickName(adminNickName);
        groupService.getGroupByAdminAndGroupName(aminEmail, applicationGroupDto.getGroupName());


        //check user status in that request group(check user is already in that group)
        //if checkUserIsInGroup didn't throw the error, other code will be processed
        String userEmail = (String) request.getAttribute("email");

        User user = userService.findUserByEmail(userEmail);
        String groupName =applicationGroupDto.getGroupName();




       if(!groupService.checkUserIsAlreadyInGroup(groupName, user)){
           groupService.applyGroup(applicationGroupDto.getAdminNickName(), applicationGroupDto.getGroupName(), user, applicationGroupDto.getUserNickName());
       }
        return ResponseEntity.status(HttpStatus.OK).body("Sign up is succeeded, admin will approve soon");

    }


    @PutMapping("/admin/handle-application")
    public ResponseEntity<Object> HandleApplication(@RequestBody GroupAuthorizationInGroupDto dto,
                                                    @RequestParam Permit permit,
                                                    @RequestParam String userNickName){

        com.module.entity.Group group = groupService.getGroupByGroupName(dto.getGroupName());
        logger.info("Group: " + group.toString());

        UserInGroup userInGroup = groupService.getUserInGroupByUserNickNameAndGroup(userNickName, group);
        logger.info("user in group: " + userInGroup.toString());

        if (permit.getPermit().equals("DENIED")) {
            userInGroup.setRole(Role.ROLE_GROUP_DENIED);
        } else {
            userInGroup.setRole(Role.ROLE_GROUP_USER);
        }

        groupService.consentOrDenyUser(userInGroup);
        return ResponseEntity.status(HttpStatus.OK).body("User status is update pending to " + permit);
    }

    @PutMapping("/admin/update-group")
    public ResponseEntity<Object> updateGroup(@RequestBody GroupAuthorizationInGroupDto dto,
                                              @RequestParam String updateName) {

        if (!groupService.groupNameIsDuplicated(updateName)) {
            Group group = groupService.getGroupByGroupName(dto.getGroupName());
            group.setName(updateName);
            groupService.updateGroup(group);
            return ResponseEntity.status(HttpStatus.OK).body("now group name is " + updateName);
        }
        throw new IllegalArgumentException("there is already same group name");
    }

    @DeleteMapping("/admin/delete-group")
    public ResponseEntity<Object> deleteGroup(@RequestBody GroupAuthorizationInGroupDto dto) {

        try {
            groupService.deleteGroup(groupService.getGroupByGroupName(dto.getGroupName()));
        }
        catch (IllegalArgumentException e){
            throw new IllegalArgumentException("the group name is not exist");
        }
        return ResponseEntity.status(HttpStatus.OK).body("complete to delete");
    }

    @PutMapping("/admin/warn")
    public ResponseEntity<Object> warnUserInGroup(@RequestBody GroupAuthorizationInGroupDto dto,
                                                  @RequestParam String userNickName) {

        UserInGroup userInGroup = groupService.getUserInGroupByUserNickNameAndGroup(userNickName,groupService.getGroupByGroupName(dto.getGroupName()));
        logger.info("user information in group " + userInGroup.toString());

        int warn = userInGroup.getWarnCount();

        if (warn < 3) {
            userInGroup.setWarnCount(++warn);
            groupService.updateUserInGroup(userInGroup);
            logger.info("user warning count is increased by 1 so now is " + warn);
            return ResponseEntity.status(HttpStatus.OK).body("user warning count is increased by 1. so now is " + warn);

        }
            groupService.kickUserInGroupOrWithdrawal(userInGroup);
            logger.info("user is in group deleted");
            return ResponseEntity.status(HttpStatus.OK).body("user is kicked");
    }

    @DeleteMapping("/user/withdrawal")
    public ResponseEntity<Object> withdrawalByMyself(@RequestBody GroupAuthorizationInGroupDto dto, @RequestParam(required = false) String userNickNameWillBeAdmin) {

        UserInGroup userInGroup = groupService.getUserInGroupByUserNickNameAndGroup(dto.getMyNickName(), groupService.getGroupByGroupName(dto.getGroupName()));
        logger.info("user information in group " + userInGroup.toString());

        if(userInGroup.getRole().withRolePrefix().equals("ROLE_GROUP_ADMIN")){

            groupService.takeOverAdmin(dto.getGroupName(), userNickNameWillBeAdmin);
            logger.info("the user take over the position now admin is :"+ userNickNameWillBeAdmin);
        }

        groupService.kickUserInGroupOrWithdrawal(userInGroup);
        return ResponseEntity.status(HttpStatus.OK).body("now you are is not member of "+ dto.getGroupName());
    }






    }