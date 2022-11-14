package com.module.group;

import com.module.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface GroupService extends UserDetailsService {

    void createGroup(String groupName, String adminEmail,String adminNickName, GroupType groupType);
    List<Group> getByType(GroupType groupType);
    Group getGroupByAdminAndGroupName(String adminNickName , String groupName);
    Group getGroupByGroupName(String groupName);
    List<Integer> getUserNumberInGroup(List<Group> groups);
    UserInGroup applyGroup(String admin , String groupName, User user, String nickName);
    UserInGroup getUserInGroupByUserNickNameAndGroup(String nickName, Group group);
    UserInGroup consentOrDenyUser(UserInGroup userInGroup);
    boolean groupNameIsDuplicated(String groupName);
}
