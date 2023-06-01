package org.example.tgbd.kafkasender;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tgbd.dto.DtoKeeper;
import org.example.tgbd.kafka.TopicProperties;
import org.example.tgbd.services.MemorizationService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component("deleteAndGetAllInformation")
@RequiredArgsConstructor
@Slf4j
public class DeleteAndGetAllInformation implements KafkaSender {

    private final MemorizationService memorizationService;
    private final KafkaTemplate<String, DtoKeeper> kafkaTemplate;
    private final TopicProperties topicProperties;

    @Override
    public void send(DtoKeeper dtoKeeper) {
        dtoKeeper = memorizationService.deletAndGetAllMessages(dtoKeeper);
        dtoKeeper.setMethodName("getAllInformation");
        log.info(dtoKeeper.getMessage());
        kafkaTemplate.send(topicProperties.getServiceTopic(), dtoKeeper);

    }
}
