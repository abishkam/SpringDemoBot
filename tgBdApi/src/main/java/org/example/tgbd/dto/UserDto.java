package org.example.tgbd.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {

    //for bd
    private Long chatId;
    private String userName;
    private List<MemorizationDto> memorizationDtos;

    //for service
    private String state;
    private String timeState;
    private short amount;
}
