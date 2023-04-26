package org.example.tgservice.config;

import lombok.Getter;
import lombok.Setter;
import org.example.tgbd.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@Getter
@Setter
public class UserInitialization {

    private final RestTemplate restTemplate = new RestTemplate();
    private UserDto userDto;

    public UserDto findUser(Long id){

        if(userDto == null){
            ResponseEntity<UserDto> userDtoResponse =
                    restTemplate.getForEntity("http://localhost:8080/user/getUserById/"+id, UserDto.class);
            this.userDto = userDtoResponse.getBody();
            Objects.requireNonNull(userDto).setState("free");
        }
        return userDto;
    }

}
