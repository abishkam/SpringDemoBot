package org.example.tgbd.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
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
