package org.example.tgbd.kafkacontroller;

import lombok.RequiredArgsConstructor;
import org.example.tgbd.dto.DtoKeeper;
import org.example.tgbd.services.MemorizationService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component("memorizationKafkaController")
@RequiredArgsConstructor
public class MemorizationKafkaController implements KafkaController {

    private final MemorizationService memorizationService;
    private final KafkaTemplate<String, DtoKeeper> kafkaTemplate;

    public void setInformation(DtoKeeper dtoKeeper) {

        dtoKeeper = DtoKeeper.builder()
                .message(memorizationService.setMessageToEntity(dtoKeeper.getUserDto(), dtoKeeper.getMemorizationDto()))
                .className("sendMessageHandler")
                .build();

        kafkaTemplate.send("serviceTopic", dtoKeeper);
    }

    public void getAllInformation(DtoKeeper dtoKeeper) {

        dtoKeeper = DtoKeeper.builder()
                .message(memorizationService.getAllMessages(dtoKeeper.getUserDto()))
                .className("getAllMessagesHandler")
                .build();
        kafkaTemplate.send("serviceTopic", dtoKeeper);

    }

    public void getMessage(DtoKeeper dtoKeeper) {

        dtoKeeper.setMessage(memorizationService.getMessage(dtoKeeper.getMemorizationDto()));
        kafkaTemplate.send("serviceTopic", dtoKeeper);

    }

    @Override
    public void request(DtoKeeper dtoKeeper) {
        if (dtoKeeper.getMethodName().equals("setInformation")) {
            setInformation(dtoKeeper);
        } else if (dtoKeeper.getMethodName().equals("getAllInformation")) {
            getAllInformation(dtoKeeper);
        } else if (dtoKeeper.getMethodName().equals("getMessage")) {
            getMessage(dtoKeeper);
        }
    }
}
