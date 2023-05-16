package org.example.tgbd.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MemorizationDto {

    private Long messageId;
    private String message;
    private List<TimeDto> timeList;
}
