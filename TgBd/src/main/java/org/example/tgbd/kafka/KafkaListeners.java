package org.example.tgbd.kafka;

import lombok.RequiredArgsConstructor;
import org.example.tgbd.dto.DtoKeeper;
import org.example.tgbd.kafkasender.KafkaSender;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class KafkaListeners {

    private final Map<String, KafkaSender> kafkaControllerList;

    @KafkaListener(topics = "bdTopic", groupId = "myGroup")
    void listener(DtoKeeper dtoKeeper) {

        Optional<KafkaSender> controllerOptional =
            kafkaControllerList.entrySet().stream()
                    .filter(i -> i.getKey().equals(dtoKeeper.getMethodName()))
                    .findFirst()
                    .map(i -> i.getValue());

        controllerOptional.ifPresent(i -> i.send(dtoKeeper));

    }

}
