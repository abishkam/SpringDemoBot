package org.example.tgbd.kafkasender;

import lombok.RequiredArgsConstructor;
import org.example.tgbd.dto.DtoKeeper;
import org.example.tgbd.kafka.TopicProperties;
import org.example.tgbd.services.TimeService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component("setTimeToMemorization")
@RequiredArgsConstructor
public class SetTimeToMemorization implements KafkaSender {

    private final TimeService timeService;
    private final KafkaTemplate<String, DtoKeeper> kafkaTemplate;
    private final TopicProperties topicProperties;

    @Override
    public void send(DtoKeeper dtoKeeper) {
        //timeService.setTime(dtoKeeper);
        dtoKeeper = DtoKeeper.builder()
                .build();
        kafkaTemplate.send(topicProperties.getTopic(), dtoKeeper);
    }
}
