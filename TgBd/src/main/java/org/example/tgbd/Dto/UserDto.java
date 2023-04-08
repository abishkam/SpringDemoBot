package org.example.tgbd.Dto;

import org.example.tgbd.model.Repeat;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {

    private Long chatId;
    private String userName;
    private List<Repeat> repeat;
}
