package org.example.tgbd.controllers;

import lombok.RequiredArgsConstructor;
import org.example.tgbd.dto.MemorizationDto;
import org.example.tgbd.dto.UserDto;
import org.example.tgbd.services.MemorizationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/memorization")
@RequiredArgsConstructor
public class MemorizationController {

    private final MemorizationService memorizationService;

    @GetMapping("/setMessage/{chatId}/{name}/{messageId}/{message}")
    public String setInformation(@PathVariable("chatId") Long chatId,
                             @PathVariable("name") String name,
                             @PathVariable("messageId") Long messageId,
                             @PathVariable("message") String message) {

//        UserDto userDto = UserDto.builder()
//                .chatId(chatId)
//                .userName(name)
//                .build();
//
//        MemorizationDto memorizationDto = MemorizationDto.builder()
//                .messageId(messageId)
//                .message(message)
//                .build();

        UserDto userDto = new UserDto();
        userDto.setChatId(chatId);
        userDto.setUserName(name);

        MemorizationDto memorizationDto = new MemorizationDto();
        memorizationDto.setMessageId(messageId);
        memorizationDto.setMessage(message);

        return memorizationService.setMessageToEntity(userDto, memorizationDto);
    }

    @GetMapping("/getAllMessages/{chatId}/{name}")
    public String getAllInformation(@PathVariable("chatId") Long chatId, @PathVariable("name") String name) {
        return memorizationService.getAllMessages(chatId, name);
    }
}
