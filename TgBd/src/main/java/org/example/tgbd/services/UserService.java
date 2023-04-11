package org.example.tgbd.services;

import lombok.RequiredArgsConstructor;
import org.example.tgbd.dto.RepeatDto;
import org.example.tgbd.dto.UserDto;
import org.example.tgbd.mapper.BotMapper;
import org.example.tgbd.model.Repeat;
import org.example.tgbd.model.User;
import org.example.tgbd.model.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BotMapper botMapper;

    public Boolean createNewUser(UserDto userDto) {

        if (userDto.getChatId() == null) {
            User save = userRepository.save(botMapper.UserDtoToModel(userDto));
            return true;
        }
        return false;
    }

    public void setMessageToEntity(final UserDto userDto, final RepeatDto repeatDto){

        Optional<User> user = userRepository.findById(userDto.getChatId());
        Repeat repeat = botMapper.RepeatDtoToModel(repeatDto);
        user.ifPresentOrElse(
                (value)
                        -> value.setRepeat(Collections.singletonList(repeat)),
                ()
                        -> {
                    User newUser = new User(userDto.getChatId(), userDto.getUserName(), Collections.singletonList(repeat));
                    userRepository.save(newUser);
                });

    }

    public String getAllMessages(final Long chatId, final String name){

        Optional<User> user = userRepository.findById(chatId);
        String message;

        if(user.isPresent()){
            if(user.get().getRepeat().isEmpty()){
                message = "You don't have any stored messages";
            } else{
                List<RepeatDto> repeatDtos = botMapper.map(user.get().getRepeat());
                message = repeatDtos
                        .stream()
                        .map(i -> String.join(" ", String.valueOf(i.getMessageId()), i.getMessage()))
                        .collect(Collectors.joining("\n"));
            }
        } else {
            message = "You don't have any stored messages";
            UserDto userDto = new UserDto();
            userDto.setChatId(chatId);
            userDto.setUserName(name);
            createNewUser(userDto);
        }

        return message;
    }



}