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

    public void userResponse(String action, Long chatId) {
        UserDto user = null;
        if (action.equals("createUser")) {
            user = UserDto.builder()
                    .chatId(chatId)
                    .state("free")
                    .build();

        }

        DtoKeeper dtoKeeper = DtoKeeper.builder()
                .userDto(user)
                .className("userKafkaController")
                .methodName(action)
                .build();

        kafkaTemplate.send("bdTopic", dtoKeeper);
    }

    public void memorizationResponse(String action, Long chatId, String messageId, String textToSend) {

        UserDto user = UserDto.builder()
                .chatId(chatId)
                .build();

        if (action.equals("setInformation")) {

            MemorizationDto memorizationDto = MemorizationDto.builder()
                    .messageId(Long.parseLong(messageId))
                    .message(textToSend)
                    .build();

            DtoKeeper dtoKeeper = DtoKeeper.builder()
                    .userDto(user)
                    .memorizationDto(memorizationDto)
                    .className("memorizationKafkaController")
                    .methodName(action)
                    .build();
            kafkaTemplate.send("bdTopic", dtoKeeper);
        } else if (action.equals("getallmessages")) {

            DtoKeeper dtoKeeper = DtoKeeper.builder()
                    .userDto(user)
                    .className("memorizationKafkaController")
                    .methodName(action)
                    .build();

            kafkaTemplate.send("bdTopic", dtoKeeper);
        }

    }

    public void timeResponse(String action, Long chatId, Integer messageId, String unitOfTime) {

        if(action.equals("setTimeToMemorization")) {
            UserDto user = UserDto.builder()
                    .chatId(chatId)
                    .build();

            DtoKeeper dtoKeeper = DtoKeeper.builder()
                    .userDto(user)
                    .className("timeKafkaController")
                    .methodName(action)
                    .build();
            kafkaTemplate.send("bdTopic", dtoKeeper);
        }
    }

    public void patternResponse(String action, Long chatId, String... variables) {

        if (action.equals("emptyMessage")) {
            DtoKeeper dtoKeeper = DtoKeeper.builder()
                    .message(variables[0])
                    .build();
            kafkaTemplate.send("serviceTopic", dtoKeeper);
        } else if (action.equals("notNumber")) {
            DtoKeeper dtoKeeper = DtoKeeper.builder()
                    .message(variables[0])
                    .build();
            kafkaTemplate.send("serviceTopic", dtoKeeper);
        } else if (action.equals("helpMessage")) {
            DtoKeeper dtoKeeper = DtoKeeper.builder()
                    .message(variables[0])
                    .build();
            kafkaTemplate.send("serviceTopic", dtoKeeper);
        }
    }


}
