package org.example.tgservice.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.tgbd.dto.UserDto;
import org.example.tgservice.kafka.KafkaSender;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class UserInitialization {

    private final KafkaSender kafkaSender;
    private UserDto userDto;

    public UserDto findUser(Long id) {

        if (userDto == null) {
            kafkaSender.userResponse("getById", id.toString());
        }
        return userDto;
    }

}
