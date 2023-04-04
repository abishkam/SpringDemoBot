package io.proj3ct.springdemobot.DTO;

import io.proj3ct.springdemobot.model.Repeat;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {

    private Long chatId;
    private String userName;
    private List<Repeat> repeat;
}
