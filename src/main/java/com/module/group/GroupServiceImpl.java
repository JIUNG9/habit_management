package com.module.group;

import com.module.room.Room;
import com.module.room.RoomRepository;
import com.module.user.Role;
import com.module.user.User;
import com.module.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final UserInGroupRepository userInGroupRepository;


    public void createGroup(String groupName, String adminEmail, String nickName, GroupType groupType) {
        //get user
        User user = Optional.ofNullable(userRepository.getUserByEmail(adminEmail)).orElseThrow(IllegalAccessError::new);
        //make group instance
        Group group = Group.builder().admin(user.getEmail()).name(groupName).groupType(groupType).build();
        //make userGroup instance
        UserInGroup userInGroup = UserInGroup.builder().user(user).group(group).role(Role.ROLE_GROUP_ADMIN).nickName(nickName).build();

        //only one group is acceptable with same admin and same group name
        Optional.ofNullable(groupRepository.getByAdminAndName(group.getAdmin(), group.getName())).ifPresent(s -> {
            throw new EntityExistsException("there is already same name group with same admin");
        });
        Optional.ofNullable(groupRepository.save(group)).orElseThrow(IllegalArgumentException::new);

        Optional.ofNullable(userInGroupRepository.save(userInGroup)).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<Group> getByType(GroupType groupType) {
        return Optional.ofNullable(groupRepository.getByType(groupType)).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Group getGroupByAdminAndGroupName(String adminNickName, String name) {
        return Optional.ofNullable(groupRepository.getByAdminAndName(adminNickName, name)).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Group getGroupByGroupName(String groupName) {
        return Optional.ofNullable(groupRepository.getGroupByName(groupName)).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Integer> getUserNumberInGroup(List<Group> groups) {
        return userInGroupRepository.countUserInGroupsByGroupIn(groups);
    }

    public UserInGroup applyGroup(String adminNickName, String groupName, User user, String nickName) {
        //nick name duplication check
        Group group = this.getGroupByAdminAndGroupName(adminNickName, groupName);
        Optional.ofNullable(userInGroupRepository.findUserInGroupByNickNameAndGroup(group, nickName)).ifPresent(s -> {
            throw new EntityExistsException("there is already same nickname in group");
        });
        UserInGroup userInGroup = UserInGroup.builder().group(group).nickName(nickName).role(Role.ROLE_GROUP_PENDING).user(user).build();
        return Optional.ofNullable(userInGroupRepository.save(userInGroup)).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public UserInGroup getUserInGroupByUserNickNameAndGroup(String userNickName, Group group) {
        return Optional.ofNullable(userInGroupRepository.findUserInGroupByNickNameAndGroup(group, userNickName)).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public UserInGroup consentOrDenyUser(UserInGroup userInGroup) {
        return Optional.ofNullable(userInGroupRepository.save(userInGroup)).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public boolean groupNameIsDuplicated(String groupName) {
        return Optional.ofNullable(groupRepository.getGroupByName(groupName)).isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
            return null;
    }
}