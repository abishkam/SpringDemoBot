package org.example.tgbd.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DtoKeeper {

    private UserDto userDto;
    private MemorizationDto memorizationDto;
    private TimeDto timeDto;

    private String className;
    private String methodName;
    private String message;

}
