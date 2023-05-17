package org.example.tgbd.services;


import lombok.RequiredArgsConstructor;

import org.example.tgbd.dto.MemorizationDto;
import org.example.tgbd.dto.UserDto;
import org.example.tgbd.mapper.BotMapper;
import org.example.tgbd.model.Memorization;
import org.example.tgbd.model.MemorizationRepository;
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
public class MemorizationService {

    private final UserRepository userRepository;
    private final MemorizationRepository memorizationRepository;
    private final BotMapper botMapper;
    private final UserService userService;

    public String setMessageToEntity(final UserDto userDto, final MemorizationDto memorizationDto) {

        Optional<User> user = userRepository.findById(userDto.getChatId());

        Memorization memorization = Memorization
            .builder()
            .messageId(memorizationDto.getMessageId())
            .message(memorizationDto.getMessage())
            .build();

        user.ifPresentOrElse(
            (value)
                -> {
                    memorization.setUser(value);
                    memorizationRepository.save(memorization);
            },
            ()
                -> {
                    User newUser = new User(userDto.getChatId(), userDto.getUserName(), Collections.singletonList(memorization), "free");
                    userRepository.save(newUser);
            });

        return "Id of a message - " + memorizationDto.getMessageId();

    }

    public String getAllMessages(final UserDto userDto) {

        Optional<User> user = userRepository.findById(userDto.getChatId());

        String message;

        if(user.isPresent()) {
            if (user.get().getMemorizations().isEmpty()) {
                message = "You don't have any saved messages";
            } else {
                List<MemorizationDto> memorizationDtos = botMapper.repeatMap(user.get().getMemorizations());
                message = memorizationDtos
                        .stream()
                        .map(i -> String.join(" ", String.valueOf(i.getMessageId()), i.getMessage()))
                        .collect(Collectors.joining("\n"));
            }
        } else {
            message = "You don't have any saved messages";

            userService.createNewUser(userDto);
        }

        return message;
    }

}
