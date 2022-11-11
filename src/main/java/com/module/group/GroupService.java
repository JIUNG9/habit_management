package com.module.group;

import java.util.List;

public interface GroupService {

    void createGroup(String groupName, String userEmail);
    List<Group> getAll();
}
