package com.module.service;

import com.module.entity.UserInGroup;
import com.module.type.GroupType;
import com.module.dto.SortByGroupUserNumberDto;
import com.module.entity.Group;
import com.module.entity.User;
import com.module.type.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface GroupService extends UserDetailsService {

    List<com.module.entity.Group> sortByType(GroupType groupType, Integer page);
    List<SortByGroupUserNumberDto> sortByUserNumberInGroup(List<com.module.entity.Group> groupList, Integer page);
    List<SortByGroupUserNumberDto> sortByNameOrder(List<com.module.entity.Group> groupList, Integer page);
    List<Integer> getUserNumberInGroup(List<com.module.entity.Group> groups);
    com.module.entity.Group getGroupByAdminAndGroupName(String adminEmail , String groupName);
    com.module.entity.Group getGroupByGroupName(String groupName);
    com.module.entity.Group updateGroup(com.module.entity.Group group);
    void createGroup(String groupName, String adminEmail, String adminNickName, GroupType groupType);
    void deleteGroup(com.module.entity.Group group);
    String getAdminEmailByAdminNickName(String adminNickName);
    UserInGroup applyGroup(String admin , String groupName, User user, String nickName);
    UserInGroup getUserInGroupByUserNickNameAndGroup(String nickName, com.module.entity.Group group);
    UserInGroup getUserInGroupByNickName(String nickName);
    UserInGroup consentOrDenyUser(UserInGroup userInGroup);
    UserInGroup updateUserInGroup(UserInGroup userInGroup);
    void takeOverAdmin(String groupName, String userNickName);
    List<UserInGroup> getMembersInGroup(String groupName);
    List<String> getAdminNickNameByGroupList(List<Group> groupList);
    void kickUserInGroupOrWithdrawal(UserInGroup userInGroup);
    boolean groupNameIsDuplicated(String groupName);
    boolean checkUserIsAlreadyInGroup(String groupName, User user);
}
