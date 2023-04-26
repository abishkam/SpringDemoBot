package org.example.tgbd.dto;

import lombok.Data;

import java.util.List;

@Data
public class MemorizationDto {

    private Long messageId;
    private String message;
    private List<TimeDto> timeList;
}
