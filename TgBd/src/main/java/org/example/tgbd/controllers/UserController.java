package org.example.tgbd.controllers;

import lombok.RequiredArgsConstructor;
import org.example.tgbd.dto.UserDto;
import org.example.tgbd.mapper.BotMapper;
import org.example.tgbd.model.UserRepository;
import org.example.tgbd.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    private final BotMapper botMapper;

    @GetMapping("/createUser/{id}/{name}")
    public String createUser(@PathVariable("id") Long id, @PathVariable("name") String name) {

//        UserDto userDto = UserDto.builder()
//                .chatId(id)
//                .userName(name)
//                .state("free")
//                .build();

        UserDto userDto = new UserDto();
        userDto.setChatId(id);
        userDto.setUserName(name);
        userDto.setState("free");

        return userService.createNewUser(userDto);
    }

    @GetMapping("/getUserById/{chatId}")
    public UserDto getById(@PathVariable("chatId") Long chatId) {

        return botMapper.UserModelToDto(userRepository.findById(chatId).get());
    }



}
