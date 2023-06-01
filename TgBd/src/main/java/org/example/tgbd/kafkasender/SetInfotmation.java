package org.example.tgbd.kafkasender;

import lombok.RequiredArgsConstructor;
import org.example.tgbd.dto.DtoKeeper;
import org.example.tgbd.kafka.TopicProperties;
import org.example.tgbd.services.MemorizationService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component("setInformation")
@RequiredArgsConstructor
public class SetInfotmation implements KafkaSender{

    private final MemorizationService memorizationService;
    private final KafkaTemplate<String, DtoKeeper> kafkaTemplate;
    private final TopicProperties topicProperties;

    public void send(DtoKeeper dtoKeeper) {
        dtoKeeper.setMessage(memorizationService.setMessageToEntity(dtoKeeper));
        kafkaTemplate.send(topicProperties.getServiceTopic(), dtoKeeper);
    }
}
