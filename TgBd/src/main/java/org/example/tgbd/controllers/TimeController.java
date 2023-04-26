package org.example.tgbd.controllers;

import lombok.RequiredArgsConstructor;

import org.example.tgbd.dto.TimeDto;
import org.example.tgbd.services.TimeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/time")
@RequiredArgsConstructor
public class TimeController {

    private final TimeService timeService;

    @GetMapping("/setTimeToMemorization/{chatId}/{messageId}/{unitOfTime}/{amount}")
    public void setTimeToInformation(@PathVariable("chatId") Long chatId,
                                       @PathVariable("messageId") Long messageId,
                                       @PathVariable("unitOfTime") String unitOfTime,
                                       @PathVariable("amount") short amount){

        timeService.setTime(chatId, messageId, new TimeDto(unitOfTime, amount));
    }
}
