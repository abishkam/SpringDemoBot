package org.example.tgbd.kafkacontroller;

import lombok.RequiredArgsConstructor;
import org.example.tgbd.dto.DtoKeeper;
import org.example.tgbd.dto.UserDto;
import org.example.tgbd.services.TimeService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component("timeKafkaController")
@RequiredArgsConstructor
public class TimeKafkaController implements KafkaController {

    private final TimeService timeService;
    private final KafkaTemplate<String, DtoKeeper> kafkaTemplate;

    public void setTimeToMemorization(DtoKeeper dtoKeeper) {
        timeService.setTime(dtoKeeper);
        dtoKeeper = DtoKeeper.builder()
                .className("numbersMessageHandler")
                .build();
        kafkaTemplate.send("serviceTopic", dtoKeeper);
    }

    public Boolean checkPresenceTime(UserDto userDto) {

        return timeService.checkPresenceTime(userDto);
    }

    @Override
    public void request(DtoKeeper dtoKeeper) {
        if (dtoKeeper.getMethodName().equals("setTimeToMemorization")) {
            setTimeToMemorization(dtoKeeper);
        } else if (dtoKeeper.getMethodName().equals("checkPresenceTime")) {

        }
    }
}
