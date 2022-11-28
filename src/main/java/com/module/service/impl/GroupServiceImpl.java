package com.module.service.impl;


import com.module.dto.SortByGroupUserNumberDto;
import com.module.entity.Group;
import com.module.entity.Room;
import com.module.entity.User;
import com.module.entity.UserInGroup;
import com.module.repository.GroupRepository;
import com.module.repository.UserInGroupRepository;
import com.module.service.GroupService;
import com.module.service.UserService;
import com.module.type.GroupType;
import com.module.type.Role;
import com.module.utils.lambda.BindParameterSupplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final UserService userService;
    private final UserInGroupRepository userInGroupRepository;


    /*

     * there is no creation room. because room will be created when group is made
     * */

    public void createGroup(String groupName, String adminEmail, String nickName, GroupType groupType) {

        User user = userService.findUserByEmail(adminEmail);

        Room room = Room.builder().name(groupName).build();

        com.module.entity.Group group = com.module.entity.Group.builder().admin(user.getEmail()).name(groupName).groupType(groupType).room(room).build();

        UserInGroup userInGroup = UserInGroup.builder().user(user).group(group).role(Role.ROLE_GROUP_ADMIN).nickName(nickName).warnCount(0).build();

        Optional.ofNullable(groupRepository.save(group)).orElseThrow(BindParameterSupplier.bind(IllegalArgumentException::new, "can not save the group"));

        Optional.ofNullable(userInGroupRepository.save(userInGroup)).orElseThrow(BindParameterSupplier.bind(IllegalArgumentException::new, "can not save the user in group"));
    }

    @Override
    public List<SortByGroupUserNumberDto> sortByUserNumberInGroup(List<com.module.entity.Group> groupList, Integer page) {

        Pageable pageable = PageRequest.of(page, 5);
        return userInGroupRepository.countGroupUserAndSortByUserNumber(groupList, pageable);

    }

    @Override
    public List<SortByGroupUserNumberDto> sortByNameOrder(List<Group> groupList, Integer page) {
        Pageable pageable = PageRequest.of(page, 5,Sort.by(Sort.Direction.ASC,"group.name"));
        return userInGroupRepository.countGroupUserAndSortByName(groupList, pageable);
    }


    @Override
    public List<com.module.entity.Group> sortByType(GroupType groupType, Integer page) {

        Pageable pageable = PageRequest.of(page, 5);
        List<com.module.entity.Group> groupList = Optional.ofNullable(groupRepository.getByType(groupType, pageable)).orElseThrow(BindParameterSupplier.bind(EntityNotFoundException::new, "there is group by group type"));

            return groupList;
}

    @Override
    public com.module.entity.Group getGroupByAdminAndGroupName(String adminEmail, String name) {
        return Optional.ofNullable(groupRepository.getGroupByAdminAndName(adminEmail, name)).orElseThrow(BindParameterSupplier.bind(EntityNotFoundException::new, "there isn't a group by admin and group"));
    }

    @Override
    public com.module.entity.Group getGroupByGroupName(String groupName) {
        return Optional.ofNullable(groupRepository.getGroupByName(groupName)).orElseThrow(BindParameterSupplier.bind(EntityNotFoundException::new, "the group is not exist by group name"));
    }

    @Override
    public com.module.entity.Group updateGroup(com.module.entity.Group group) {
        return Optional.ofNullable(groupRepository.save(group)).orElseThrow(BindParameterSupplier.bind(IllegalArgumentException::new, "can not update the group"));
    }

    @Override
    public void deleteGroup(com.module.entity.Group group) {

        try {
            groupRepository.delete(group);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("can not delete the group check the input group information");
        }
    }

    @Override
    public String getAdminEmailByAdminNickName(String adminNickName) {

        UserInGroup userInGroup =
                Optional.ofNullable(
                    userInGroupRepository.findUserInGroupByNickName(adminNickName)).
                        orElseThrow(BindParameterSupplier.bind(EntityNotFoundException::new, "there is no admin user with that nick name in group"));
                            return userInGroup.getGroup().getAdmin();
    }



    @Override
    public List<Integer> getUserNumberInGroup(List<com.module.entity.Group> groups) {
        return userInGroupRepository.countUserInGroupsByGroupIn(groups);
    }

    public UserInGroup applyGroup(String adminNickName, String groupName, User user, String nickName) {

        String adminEmail = this.getAdminEmailByAdminNickName(adminNickName);
        //the getGroupByAdminAndGroupName method's first parameter which name is admin is a Email of admin
        com.module.entity.Group group = this.getGroupByAdminAndGroupName(adminEmail, groupName);

        Optional.ofNullable(userInGroupRepository.findUserInGroupByGroupAndNickName(group, nickName)).ifPresent(s -> {
            throw new EntityExistsException("there is already same nickname in group");
        });
        UserInGroup userInGroup = UserInGroup.builder().group(group).nickName(nickName).role(Role.ROLE_GROUP_PENDING).user(user).build();
        return Optional.ofNullable(userInGroupRepository.save(userInGroup)).orElseThrow(BindParameterSupplier.bind(IllegalArgumentException::new, "can't save the user in user group"));
    }

    @Override
    public UserInGroup getUserInGroupByUserNickNameAndGroup(String userNickName, com.module.entity.Group group) {
        return Optional.ofNullable(userInGroupRepository.findUserInGroupByGroupAndNickName(group, userNickName)).orElseThrow(BindParameterSupplier.bind(EntityNotFoundException::new, "there is no user with that nick name in that group"));
    }


    @Override
    public UserInGroup getUserInGroupByNickName(String nickName) {
        return Optional.ofNullable(userInGroupRepository.findUserInGroupByNickName(nickName)).orElseThrow(BindParameterSupplier.bind(EntityNotFoundException::new, "there is no  user with that nick name in user group"));
    }

    @Override
    public UserInGroup consentOrDenyUser(UserInGroup userInGroup) {
        return Optional.ofNullable(userInGroupRepository.save(userInGroup)).orElseThrow(BindParameterSupplier.bind(IllegalArgumentException::new, "can't save the denied or consented user"));
    }

    @Override
    public UserInGroup updateUserInGroup(UserInGroup userInGroup) {
        return Optional.ofNullable(userInGroupRepository.save(userInGroup)).orElseThrow(BindParameterSupplier.bind(IllegalArgumentException::new, "can't save warned user"));

    }

    @Override
    public void takeOverAdmin(String groupName, String userNickName) {

         UserInGroup userInGroup =
                 Optional.ofNullable(userInGroupRepository.findUserInGroupByGroupAndNickName(this.getGroupByGroupName(groupName),userNickName))
                            .orElseThrow(BindParameterSupplier.bind(EntityNotFoundException::new, "there is no user in that group. so can't take over the admin position"));

                                userInGroup.setRole(Role.ROLE_GROUP_ADMIN);
                                this.updateUserInGroup(userInGroup);
    }


    @Override
    public List<UserInGroup> getMembersInGroup(String groupName) {

        log.info(this.getGroupByGroupName(groupName).toString());
        try {
            return userInGroupRepository.findUserInGroupsByGroup(this.getGroupByGroupName(groupName));
        }
        catch (NullPointerException e){
            throw new NullPointerException("there is no user with that group");
        }
    }

    @Override
    public List<String> getAdminNickNameByGroupList(List<Group> groupList) {

        return Optional.ofNullable(
                userInGroupRepository.getNickNameByGroupAndAdmin(groupList,Role.ROLE_GROUP_ADMIN))
                .orElseThrow(
                        BindParameterSupplier.bind(EntityNotFoundException::new, "the groupList is empty"));

    }

    @Override
    public void userWithdrawalUserSelf(UserInGroup userInGroup) {
        try{
            userInGroupRepository.delete(userInGroup);
        }
        catch (IllegalArgumentException e){
            new IllegalArgumentException("there is no user in that group");
        }

    }

    @Override
    public boolean groupNameIsDuplicated(String groupName) {
        return Optional.ofNullable(groupRepository.getGroupByName(groupName)).isPresent();
    }

    @Override
    public boolean checkUserIsAlreadyInGroup(String groupName, User user) {

        int theNumberOfUser =userInGroupRepository.findUserInGroupsByUserAndGroup(this.getGroupByGroupName(groupName), user).size();

        if(theNumberOfUser>0) {
            throw new EntityExistsException("you are already user with group name" + groupName);
        }
            return false;
    }


    @Override
    public UserDetails loadUserByUsername(String nickName) throws UsernameNotFoundException {
        //1. get user by nickName(nickName is unique)
        UserInGroup userInGroup = this.getUserInGroupByNickName(nickName);
        //2. set the authorization by the entity
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        //3. add the authority
        authorities.add(new SimpleGrantedAuthority(userInGroup.getRole().name()));
        UserDetails user = new org.springframework.security.core.userdetails.User("check", "check", authorities);
        return user;
    }
}