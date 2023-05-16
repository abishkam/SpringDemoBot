package org.example.tgbd.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeDto {

    private long id;
    private String unitOfTime;
    private short amount;

    public TimeDto(String unitOfTime, short amount){
        this.unitOfTime = unitOfTime;
        this.amount = amount;
    }
}
