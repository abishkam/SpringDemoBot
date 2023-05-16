package org.example.tgbd.kafkacontroller;

import lombok.RequiredArgsConstructor;
import org.example.tgbd.dto.DtoKeeper;
import org.example.tgbd.services.MemorizationService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component("memorizationKafkaController")
@RequiredArgsConstructor
public class MemorizationKafkaSender implements KafkaSender {

    private final MemorizationService memorizationService;
    private final KafkaTemplate<String, DtoKeeper> kafkaTemplate;

    public void setInformation(DtoKeeper dtoKeeper) {
        System.out.println(dtoKeeper);

        dtoKeeper.setMessage(memorizationService.setMessageToEntity(dtoKeeper.getUserDto(), dtoKeeper.getMemorizationDto()));

        kafkaTemplate.send("serviceTopic", dtoKeeper);
    }

    public void getallmessages(DtoKeeper dtoKeeper) {

        dtoKeeper = DtoKeeper.builder()
                .message(memorizationService.getAllMessages(dtoKeeper.getUserDto()))
                .methodName(dtoKeeper.getMethodName())
                .className("getAllMessagesHandler")
                .build();
        kafkaTemplate.send("serviceTopic", dtoKeeper);

    }

    public void getMessage(DtoKeeper dtoKeeper) {

        dtoKeeper.setMessage(memorizationService.getMessage(dtoKeeper.getMemorizationDto()));
        dtoKeeper.setMethodName(dtoKeeper.getMethodName());
        kafkaTemplate.send("serviceTopic", dtoKeeper);

    }

    @Override
    public void request(DtoKeeper dtoKeeper) {
        if (dtoKeeper.getMethodName().equals("setInformation")) {
            setInformation(dtoKeeper);
        } else if (dtoKeeper.getMethodName().equals("getallmessages")) {
            getallmessages(dtoKeeper);
        } else if (dtoKeeper.getMethodName().equals("getMessage")) {
            getMessage(dtoKeeper);
        }
    }
}
