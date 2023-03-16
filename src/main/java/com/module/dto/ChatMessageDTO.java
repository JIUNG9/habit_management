package com.module.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ChatMessageDTO {

    private String roomId;
    private String writer;
    private String message;

}

