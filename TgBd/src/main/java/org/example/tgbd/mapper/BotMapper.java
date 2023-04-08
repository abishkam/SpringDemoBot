package org.example.tgbd.mapper;

import org.example.tgbd.Dto.RepeatDto;
import org.example.tgbd.Dto.UserDto;
import org.example.tgbd.model.Repeat;
import org.example.tgbd.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BotMapper {

    RepeatDto RepeatModelToDto(Repeat repeat);

    Repeat RepeatDtoToModel(RepeatDto repeatDto);

    UserDto UserModelToDto(User user);

    User UserDtoToModel(UserDto userDto);

    List<RepeatDto> map(List<Repeat> repeat);

}
