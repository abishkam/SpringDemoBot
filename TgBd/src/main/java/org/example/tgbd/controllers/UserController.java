package org.example.tgbd.controllers;

import lombok.RequiredArgsConstructor;
import org.example.tgbd.dto.RepeatDto;
import org.example.tgbd.dto.UserDto;
import org.example.tgbd.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/createUser/{id}/{name}")
    public Boolean createUser(@PathVariable("id") Long id, @PathVariable("name") String name) {
        UserDto userDto = new UserDto();
        userDto.setChatId(id);
        userDto.setUserName(name);
        return userService.createNewUser(userDto);
    }

    @GetMapping("/user/setMessage/{chatId}/{name}/{messageId}/{message}")
    public void setMessage(@PathVariable("chatId") Long chatId, @PathVariable("name") String name, @PathVariable("messageId") Long messageId, @PathVariable("message") String message) {
        UserDto userDto = new UserDto();
        userDto.setChatId(chatId);
        userDto.setUserName(name);
        RepeatDto repeatDto = new RepeatDto();
        repeatDto.setMessageId(messageId);
        repeatDto.setMessage(message);
        userService.setMessageToEntity(userDto, repeatDto);
    }

    @GetMapping("/user/getAllMessages/{chatId}/{name}")
    public String getAllMessages(@PathVariable("chatId") Long chatId, @PathVariable("name") String name) {
        return userService.getAllMessages(chatId, name);
    }

}
