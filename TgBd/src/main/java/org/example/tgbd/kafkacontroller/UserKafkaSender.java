package org.example.tgbd.kafkacontroller;

import lombok.RequiredArgsConstructor;
import org.example.tgbd.dto.DtoKeeper;
import org.example.tgbd.services.UserService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component("userKafkaController")
@RequiredArgsConstructor
public class UserKafkaSender implements KafkaSender {

    private final UserService userService;

    private final KafkaTemplate<String, DtoKeeper> kafkaTemplate;

    public void createUser(DtoKeeper dtoKeeper) {
        dtoKeeper.setMessage(userService.createNewUser(dtoKeeper.getUserDto()));

        kafkaTemplate.send("serviceTopic", dtoKeeper);

    }

    public void getById(DtoKeeper dtoKeeper) {

        dtoKeeper = DtoKeeper.builder()
                .userDto(userService.getById(dtoKeeper))
                .className("userInitialization")
                .methodName(dtoKeeper.getMethodName())
                .build();

        kafkaTemplate.send("serviceTopic", dtoKeeper);
    }

    public void request(DtoKeeper dtoKeeper) {

        if (dtoKeeper.getMethodName().equals("createUser")) {
            createUser(dtoKeeper);
        } else if (dtoKeeper.getMethodName().equals("getById")) {

            getById(dtoKeeper);
        }

    }


}
