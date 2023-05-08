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

    public void userResponse(String action, String... variables) {
        UserDto user = null;
        if (action.equals("createUser")) {
            user = UserDto.builder()
                    .chatId(Long.parseLong(variables[0]))
                    .userName(variables[1])
                    .state("free")
                    .build();

        } else if (action.equals("getById")) {

            user = UserDto.builder()
                    .chatId(Long.parseLong(variables[0]))
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

    public void memorizationResponse(String action, String... variables) {

        if (action.equals("setInformation")) {
            UserDto user = UserDto.builder()
                    .chatId(Long.parseLong(variables[0]))
                    .userName(variables[1])
                    .state("free")
                    .build();

            MemorizationDto memorizationDto = MemorizationDto.builder()
                    .messageId(Long.parseLong(variables[2]))
                    .message(variables[3])
                    .build();

            DtoKeeper dtoKeeper = DtoKeeper.builder()
                    .userDto(user)
                    .memorizationDto(memorizationDto)
                    .className("memorizationKafkaController")
                    .methodName(action)
                    .build();
            kafkaTemplate.send("bdTopic", dtoKeeper);
        } else if (action.equals("getAllInformation")) {
            UserDto user = UserDto.builder()
                    .chatId(Long.parseLong(variables[0]))
                    .userName(variables[1])
                    .state("free")
                    .build();

            DtoKeeper dtoKeeper = DtoKeeper.builder()
                    .userDto(user)
                    .className("memorizationKafkaController")
                    .methodName(action)
                    .build();

            kafkaTemplate.send("bdTopic", dtoKeeper);
        }

    }

    public void timeResponse(String action, String... variables) {

        if(action.equals("setTimeToMemorization")) {
            UserDto user = UserDto.builder()
                    .state(variables[0])
                    .timeState(variables[1])
                    .amount(Short.parseShort(variables[2]))
                    .build();

            DtoKeeper dtoKeeper = DtoKeeper.builder()
                    .userDto(user)
                    .className("timeKafkaController")
                    .methodName(action)
                    .build();
            kafkaTemplate.send("bdTopic", dtoKeeper);
        }
    }

    public void patternResponse(String action, String... variables) {

        if (action.equals("emptyMessage")) {
            DtoKeeper dtoKeeper = DtoKeeper.builder()
                    .message(variables[0])
                    .build();
            kafkaTemplate.send("tgService", dtoKeeper);
        } else if (action.equals("notNumber")) {
            DtoKeeper dtoKeeper = DtoKeeper.builder()
                    .message(variables[0])
                    .build();
            kafkaTemplate.send("tgService", dtoKeeper);
        } else if (action.equals("helpMessage")) {
            DtoKeeper dtoKeeper = DtoKeeper.builder()
                    .message(variables[0])
                    .build();
            kafkaTemplate.send("tgService", dtoKeeper);
        }
    }


}
