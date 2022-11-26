package com.module.repository;

import com.module.entity.Group;
import com.module.entity.Room;
import com.module.type.GroupType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<com.module.entity.Group,Integer> {

    @Query("SELECT t FROM Group t where t.groupType =:group_type ")
    public List<com.module.entity.Group> getByType(@Param("group_type") GroupType group_type, Pageable pageable);

    @Query("SELECT t FROM Group t where t.name =:name and t.admin =:admin")
    public com.module.entity.Group getGroupByAdminAndName(@Param("admin") String admin, @Param("name") String name);

    @Query("SELECT t FROM Group t WHERE t.admin =:adminEmail")
    public com.module.entity.Group getGroupByAdminEmail(@Param("admin") String adminEmail);

    @Query("SELECT t FROM Group t where t.name =:name")
    public Group getGroupByName(@Param("name") String name);

    @Query("SELECT t.room FROM Group t WHERE t.id = :id")
    public Room findRoomByGroupId(@Param("id") Integer id);


}
