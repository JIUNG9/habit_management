package com.module.group;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAndGroupRepository extends JpaRepository<UserGroup, Integer> {

}
