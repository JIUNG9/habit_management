package com.module.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@AllArgsConstructor
public class ChatMessageDTO {

    private String roomId;
    private String writer;
    private String message;

}

