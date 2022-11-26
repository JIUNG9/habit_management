package com.module.service;

import com.module.entity.Group;
import com.module.entity.Room;

public interface RoomService {

    public Room findRoomByGroup(Group group);
}
