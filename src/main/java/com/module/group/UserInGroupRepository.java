package com.module.group;

import com.module.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserInGroupRepository extends JpaRepository<UserInGroup, Integer> {

    @Query("SELECT COUNT(u) FROM UserInGroup u where u.group IN(:group_id) group by u.group")
    public List<Integer> countUserInGroupsByGroupIn( @Param("group_id")List<Group> group_id);

    //nickName duplication check
    @Query("SELECT u FROM UserInGroup u where u.group = :group_id AND u.nickName = :nick_name")
    public UserInGroup findUserInGroupByNickNameAndGroup(@Param("group_id") Group group_id, @Param("nick_name") String nick_name);

    @Query("SELECT u FROM UserInGroup u where u.nickName = :nick_name")
    public UserInGroup findUserInGroupByNickName(@Param("gr")String nick_name);
}
