package org.example.tgbd.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {

    private Long chatId;
    private String userName;
    private List<RepeatDto> repeatDtos;
}
