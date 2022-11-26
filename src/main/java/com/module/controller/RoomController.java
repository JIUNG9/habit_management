package com.module.controller;


import com.module.entity.Group;
import com.module.entity.Room;
import com.module.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/group/user/chat")
@Log4j2
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/room")
    public ResponseEntity<Object> getRoom(Group group) {

        Room room =roomService.findRoomByGroup(group);
        log.info("# get Chat Room, roomID : " + room.getId());

        return ResponseEntity.status(HttpStatus.OK).body(room);

    }
}
