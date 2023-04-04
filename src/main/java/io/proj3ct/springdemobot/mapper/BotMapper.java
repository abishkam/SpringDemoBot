package io.proj3ct.springdemobot.mapper;

import io.proj3ct.springdemobot.DTO.RepeatDTO;
import io.proj3ct.springdemobot.DTO.UserDTO;
import io.proj3ct.springdemobot.model.Repeat;
import io.proj3ct.springdemobot.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BotMapper {

    RepeatDTO RepeatModelToDTO(Repeat repeat);
    Repeat RepeatDTOToModel(RepeatDTO repeatDTO);
    UserDTO UserModelToDTO(User user);
    User UserDTOToModel(UserDTO userDTO);
    List<RepeatDTO> map(List<Repeat> repeat);

}
