package org.example.tgbd.kafkasender;

import lombok.RequiredArgsConstructor;
import org.example.tgbd.dto.DtoKeeper;
import org.example.tgbd.kafka.TopicProperties;
import org.example.tgbd.services.UserService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component("createUser")
@RequiredArgsConstructor
public class CreateUser implements KafkaSender {

    private final UserService userService;
    private final KafkaTemplate<String, DtoKeeper> kafkaTemplate;
    private final TopicProperties topicProperties;

    @Override
    public void send(DtoKeeper dtoKeeper) {
        dtoKeeper.setMessage(userService.createNewUser(dtoKeeper.getUserDto()));
        kafkaTemplate.send(topicProperties.getTopic(), dtoKeeper);
    }
}
