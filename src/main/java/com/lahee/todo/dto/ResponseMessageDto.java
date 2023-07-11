package com.lahee.todo.dto;

import com.lahee.todo.domain.GuestBookMessage;
import lombok.Data;

@Data
public class ResponseMessageDto {
    private Long id;
    private String name;
    private String content;
    private int heartCnt;

    public static ResponseMessageDto fromEntity(GuestBookMessage guestBookMessage) {
        ResponseMessageDto responseMessageDto = new ResponseMessageDto();
        responseMessageDto.content = guestBookMessage.getContent();
        responseMessageDto.heartCnt = guestBookMessage.getHeartCnt();
        responseMessageDto.name = guestBookMessage.getName();
        responseMessageDto.id = guestBookMessage.getId();
        return responseMessageDto;
    }
}
