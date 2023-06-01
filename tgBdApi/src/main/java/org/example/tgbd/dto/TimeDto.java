package org.example.tgbd.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TimeDto {

    private long id;
    private boolean flag;
    private String unitOfTime;
    private short amount;
    private Date dateToReturn;
    private MemorizationDto memorizationDto;

    public TimeDto(String unitOfTime, short amount){
        this.unitOfTime = unitOfTime;
        this.amount = amount;
    }
}
