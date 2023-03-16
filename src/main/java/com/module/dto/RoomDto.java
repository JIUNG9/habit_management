package com.module.dto;

import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;


@Data
public class RoomDto {
    private String roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();

    public static RoomDto create(String name) {

        RoomDto room = new RoomDto();
        room.name = name;
        return room;
    }
}
