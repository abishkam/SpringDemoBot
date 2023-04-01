package io.proj3ct.springdemobot.DTO;

import io.proj3ct.springdemobot.model.Repeat;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class UserDTO {

    private Long chatId;
    private String userName;
    private Timestamp registeredAt;
    private List<Repeat> repeat;
}
