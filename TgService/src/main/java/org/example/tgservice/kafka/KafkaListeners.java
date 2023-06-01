package org.example.tgservice.kafka;

import lombok.RequiredArgsConstructor;
import org.example.tgbd.dto.DtoKeeper;
import org.example.tgservice.kafka.listeners.Listeners;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class KafkaListeners {

    private final List<Listeners> listenersList;

    @KafkaListener(topics = "serviceTopic", groupId = "myGroup")
    void listener(DtoKeeper dtoKeeper) {

        listenersList.stream()
                .filter(i -> i.support(dtoKeeper.getMethodName()))
                .findAny()
                .get()
                .send(dtoKeeper);

    }

}
