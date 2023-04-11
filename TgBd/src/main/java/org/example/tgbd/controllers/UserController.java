package org.example.tgbd.controllers;

import lombok.RequiredArgsConstructor;
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
        return userService.createNewUser(id, name);
    }

    @GetMapping("/user/setMessage/{chatId}/{name}/{messageId}/{message}")
    public void setMessage(@PathVariable("chatId") Long chatId, @PathVariable("name") String name, @PathVariable("messageId") Long messageId, @PathVariable("message") String message) {
        userService.setMessageToEntity(chatId, name, messageId, message);
    }

    @GetMapping("/user/getAllMessages/{chatId}/{name}")
    public String getAllMessages(@PathVariable("chatId") Long chatId, @PathVariable("name") String name) {
        return userService.getAllMessages(chatId, name);
    }

}
