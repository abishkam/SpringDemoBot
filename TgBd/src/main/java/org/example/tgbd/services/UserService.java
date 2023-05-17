package org.example.tgbd.services;

import lombok.RequiredArgsConstructor;
import org.example.tgbd.dto.DtoKeeper;
import org.example.tgbd.dto.UserDto;
import org.example.tgbd.mapper.BotMapper;
import org.example.tgbd.model.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BotMapper botMapper;

    public String createNewUser(UserDto userDto) {

        if (userRepository.findById(userDto.getChatId()).isEmpty()) {
            userRepository.save(botMapper.userDtoToModel(userDto));
            return "Hi, " + userDto.getUserName() + ", nice to meet you!";
        }

        return "You were already registered";
    }

    public UserDto getById(DtoKeeper dtoKeeper) {

        return botMapper.userModelToDto(userRepository.findById(dtoKeeper.getUserDto().getChatId()).get());
    }

}