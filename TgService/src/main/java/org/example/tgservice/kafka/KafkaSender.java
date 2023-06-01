package org.example.tgservice.kafka;

import lombok.RequiredArgsConstructor;
import org.example.tgbd.dto.DtoKeeper;
import org.example.tgbd.dto.MemorizationDto;
import org.example.tgbd.dto.TimeDto;
import org.example.tgbd.dto.UserDto;
import org.example.tgservice.property.patterns.UnitOfTime;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;

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

    public void memorizationResponse(String action, Long chatId, Integer messageIdToReturn, Integer messageId) {

        MemorizationDto memorizationDtoOfUser = MemorizationDto.builder()
                .messageId(Long.valueOf(messageId))
                .build();

        UserDto user = UserDto.builder()
                .chatId(chatId)
                .memorizationDtos(Collections.singletonList(memorizationDtoOfUser))
                .build();

        MemorizationDto memorizationDtoOfDtoKeeper = MemorizationDto.builder()
                .messageId(Long.valueOf(messageIdToReturn))
                .build();

        DtoKeeper dtoKeeper = DtoKeeper.builder()
                .userDto(user)
                .memorizationDto(memorizationDtoOfDtoKeeper)
                .methodName(action)
                .build();

        kafkaTemplate.send(topicProperties.getBdTopic(), dtoKeeper);

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

    public void timeResponse(String action, Long chatId, Long messageId, UnitOfTime unitOfTime ) {

        if(action.equals("setUnitOfTime")) {

            UserDto userDto = UserDto.builder()
                    .chatId(chatId)
                    .build();

            MemorizationDto memorizationDto = MemorizationDto.builder()
                    .messageId(messageId)
                    .build();

            TimeDto timeDto = TimeDto.builder()
                    .unitOfTime(unitOfTime.name())
                    .build();

            DtoKeeper dtoKeeper = DtoKeeper.builder()
                    .userDto(userDto)
                    .memorizationDto(memorizationDto)
                    .timeDto(timeDto)
                    .methodName(action)
                    .build();

            kafkaTemplate.send(topicProperties.getBdTopic(), dtoKeeper);
        }
    }

    public void timeResponse(String action, Long chatId, short amountOfTime ) {

        if(action.equals("setAmountOfTime")) {
            UserDto userDto = UserDto.builder()
                    .chatId(chatId)
                    .build();

            TimeDto timeDto = TimeDto.builder()
                    .amount(amountOfTime)
                    .build();

            DtoKeeper dtoKeeper = DtoKeeper.builder()
                    .userDto(userDto)
                    .timeDto(timeDto)
                    .methodName(action)
                    .build();

            kafkaTemplate.send(topicProperties.getBdTopic(), dtoKeeper);
        }
    }


    public void timeResponse(String action, Long chatId, Long messageToReturn, Long messageId) {

        MemorizationDto memorizationDtoOfUser = MemorizationDto.builder()
                .messageId(messageId)
                .build();

        UserDto user = UserDto.builder()
                .chatId(chatId)
                .memorizationDtos(Collections.singletonList(memorizationDtoOfUser))
                .build();

        MemorizationDto memorizationDtoOfDtoKeeper = MemorizationDto.builder()
                .messageId(Long.valueOf(messageToReturn))
                .build();

        DtoKeeper dtoKeeper = DtoKeeper.builder()
                .userDto(user)
                .memorizationDto(memorizationDtoOfDtoKeeper)
                .methodName(action)
                .build();

        kafkaTemplate.send(topicProperties.getBdTopic(), dtoKeeper);
    }

    public void timeResponse(String action, Long chatId, Long messageToReturn, Long messageId, Long timeId) {

        MemorizationDto memorizationDtoOfUser = MemorizationDto.builder()
                .messageId(messageId)
                .build();

        UserDto user = UserDto.builder()
                .chatId(chatId)
                .memorizationDtos(Collections.singletonList(memorizationDtoOfUser))
                .build();

        TimeDto timeDto = TimeDto.builder()
                .id(timeId)
                .build();

        MemorizationDto memorizationDtoOfDtoKeeper = MemorizationDto.builder()
                .messageId(messageToReturn)
                .build();

        DtoKeeper dtoKeeper = DtoKeeper.builder()
                .userDto(user)
                .timeDto(timeDto)
                .memorizationDto(memorizationDtoOfDtoKeeper)
                .methodName(action)
                .build();

        kafkaTemplate.send(topicProperties.getBdTopic(), dtoKeeper);
    }
}
