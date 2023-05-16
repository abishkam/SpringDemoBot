package org.example.tgbd.mapper;

import org.example.tgbd.dto.MemorizationDto;
import org.example.tgbd.dto.TimeDto;
import org.example.tgbd.dto.UserDto;
import org.example.tgbd.model.Memorization;
import org.example.tgbd.model.Time;
import org.example.tgbd.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BotMapper {

    @Mapping(target = "messageId", source = "messageId")
    @Mapping(target = "message", source = "message")
    @Mapping(target = "timeList", source = "timeList")
    MemorizationDto memorizationModelToDto(Memorization memorization);

    @Mapping(target = "messageId", source = "messageId")
    @Mapping(target = "message", source = "message")
    @Mapping(target = "timeList", source = "timeList")
    @Mapping(target = "user", ignore = true)
    Memorization memorizationDtoToModel(MemorizationDto memorizationDto);

    @Mapping(target = "chatId", source = "chatId")
    @Mapping(target = "userName", source = "userName")
    @Mapping(target = "memorizationDtos", source = "memorizations")
    @Mapping(target = "state", source = "userState")
    @Mapping(target = "timeState", ignore = true)
    @Mapping(target = "amount", ignore = true)
    UserDto userModelToDto(User user);

    @Mapping(target = "chatId", source = "chatId")
    @Mapping(target = "userName", source = "userName")
    @Mapping(target = "memorizations", source = "memorizationDtos")
    @Mapping(target = "userState", source = "state")
    User userDtoToModel(UserDto userDto);

    List<MemorizationDto> repeatMap(List<Memorization> memorizations);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "unitOfTime", source = "unitOfTime")
    @Mapping(target = "amount", source = "amount")
    TimeDto timeModelToDto(Time time);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "unitOfTime", source = "unitOfTime")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "memorization", ignore = true)
    Time timeDtoToModel(TimeDto timeDto);

    List<TimeDto> timeDtoMap(List<Time> repeat);

    List<Time> timeModelMap(List<TimeDto> repeat);

}
