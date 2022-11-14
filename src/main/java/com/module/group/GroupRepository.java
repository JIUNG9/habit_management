package com.module.group;

import com.module.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group,Integer> {

    @Query("SELECT t FROM Group t where t.groupType =:group_type ")
    public List<Group> getByType(@Param("group_type")GroupType group_type);

    @Query("SELECT t FROM Group t where t.name =:name and t.admin =:admin")
    public Group getByAdminAndName(@Param("admin") String admin, @Param("name") String name);

    @Query("SELECT t FROM Group t where t.name =:name")
    public Group getGroupByName(@Param("name") String name);


}
