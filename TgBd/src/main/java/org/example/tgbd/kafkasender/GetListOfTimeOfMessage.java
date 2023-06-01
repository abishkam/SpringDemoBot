package org.example.tgbd.kafkasender;

import lombok.RequiredArgsConstructor;
import org.example.tgbd.dto.DtoKeeper;
import org.example.tgbd.kafka.TopicProperties;
import org.example.tgbd.services.TimeService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component("getListOfTimeOfMessage")
@RequiredArgsConstructor
public class GetListOfTimeOfMessage implements KafkaSender{


    private final TimeService timeService;
    private final KafkaTemplate<String, DtoKeeper> kafkaTemplate;
    private final TopicProperties topicProperties;

    @Override
    public void send(DtoKeeper dtoKeeper) {
        dtoKeeper = timeService.getListOfTimeOfMessage(dtoKeeper);

        kafkaTemplate.send(topicProperties.getTopic(), dtoKeeper);
    }
}
