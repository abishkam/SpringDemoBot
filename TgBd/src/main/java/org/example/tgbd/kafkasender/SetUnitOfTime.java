package org.example.tgbd.kafkasender;

import lombok.RequiredArgsConstructor;
import org.example.tgbd.dto.DtoKeeper;
import org.example.tgbd.services.TimeService;
import org.springframework.stereotype.Component;

@Component("setUnitOfTime")
@RequiredArgsConstructor
public class SetUnitOfTime implements KafkaSender {

    private final TimeService timeService;

    @Override
    public void send(DtoKeeper dtoKeeper) {
        timeService.setUnitOfTime(dtoKeeper);
    }
}
