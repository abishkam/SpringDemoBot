package org.example.tgbd.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class DtoKeeper {

    private UserDto userDto;
    private List<ReturnMessageInformation> informationList;
    private MemorizationDto memorizationDto;
    private TimeDto timeDto;
    private String methodName;
    private String message;
}
