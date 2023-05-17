package org.example.tgbd.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TimeDto {

    private long id;
    private String unitOfTime;
    private short amount;

    public TimeDto(String unitOfTime, short amount){
        this.unitOfTime = unitOfTime;
        this.amount = amount;
    }
}
