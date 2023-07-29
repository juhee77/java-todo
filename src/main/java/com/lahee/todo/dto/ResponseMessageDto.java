package com.lahee.todo.dto;

import com.lahee.todo.domain.GuestBookMessage;
import lombok.Data;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Data
public class ResponseMessageDto {
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy/MM/dd HH시 mm분");
    public static final String ASIA_SEOUL = "Asia/Seoul";
    private Long id;
    private String time;
    private String name;
    private String content;
    private int heartCnt;

    public static ResponseMessageDto fromEntity(GuestBookMessage guestBookMessage) {
        ResponseMessageDto responseMessageDto = new ResponseMessageDto();
        responseMessageDto.content = guestBookMessage.getContent();
        responseMessageDto.heartCnt = guestBookMessage.getHeartCnt();
        responseMessageDto.name = guestBookMessage.getName();
        responseMessageDto.id = guestBookMessage.getId();

        responseMessageDto.time = DATE_FORMAT.format(guestBookMessage.getCreatedAt().atZone(ZoneId.of(ASIA_SEOUL)));
//        responseMessageDto.time = guestBookMessage.getCreatedAt().atZone(ZoneId.of(ASIA_SEOUL)).toString();
        return responseMessageDto;
    }

}
