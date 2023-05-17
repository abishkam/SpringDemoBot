package org.example.tgservice.kafka;

import lombok.RequiredArgsConstructor;
import org.example.tgbd.dto.DtoKeeper;
import org.example.tgbd.dto.MemorizationDto;
import org.example.tgbd.dto.UserDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaSender {

    private final KafkaTemplate<String, DtoKeeper> kafkaTemplate;
    private final TopicProperties topicProperties;

    public void userResponse(String action, Long chatId) {
        UserDto user = UserDto.builder()
                .chatId(chatId)
                .build();

        DtoKeeper dtoKeeper = DtoKeeper.builder()
                .userDto(user)
                .methodName(action)
                .build();
        kafkaTemplate.send(topicProperties.getBdTopic(), dtoKeeper);
    }

    public void memorizationResponse(String action, Long chatId) {

        UserDto user = UserDto.builder()
                .chatId(chatId)
                .build();

        if (action.equals("getAllInformation")) {

            DtoKeeper dtoKeeper = DtoKeeper.builder()
                    .userDto(user)
                    .methodName(action)
                    .build();

            kafkaTemplate.send(topicProperties.getBdTopic(), dtoKeeper);
        }

    }

    public void memorizationResponse(String action, Long chatId, Integer messageId, String textToSend) {

        UserDto user = UserDto.builder()
                .chatId(chatId)
                .build();

        if (action.equals("setInformation")) {

            MemorizationDto memorizationDto = MemorizationDto.builder()
                    .messageId(Long.valueOf(messageId))
                    .message(textToSend)
                    .build();

            DtoKeeper dtoKeeper = DtoKeeper.builder()
                    .userDto(user)
                    .memorizationDto(memorizationDto)
                    .methodName(action)
                    .build();
            kafkaTemplate.send(topicProperties.getBdTopic(), dtoKeeper);
        }

    }

    public void patternResponse(String action, Long chatId, String patternMessage) {

        UserDto user = UserDto.builder()
                .chatId(chatId)
                .build();

        DtoKeeper dtoKeeper = DtoKeeper.builder()
                .userDto(user)
                .message(patternMessage)
                .methodName(action)
                .build();
        kafkaTemplate.send(topicProperties.getServiceTopic(), dtoKeeper);

    }

//    public void timeResponse(String action, String... variables) {
//
//        if(action.equals("setTimeToMemorization")) {
//            UserDto user = UserDto.builder()
//                    .state(variables[0])
//                    .timeState(variables[1])
//                    .amount(Short.parseShort(variables[2]))
//                    .build();
//
//            DtoKeeper dtoKeeper = DtoKeeper.builder()
//                    .userDto(user)
//                    .className("timeKafkaController")
//                    .methodName(action)
//                    .build();
//            kafkaTemplate.send(System.getenv("bd"), dtoKeeper);
//        }
//    }




}
