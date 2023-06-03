package org.example.tgservice.kafka.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tgbd.dto.DtoKeeper;
import org.example.tgbd.dto.ReturnMessageInformation;
import org.example.tgservice.TelegramBot;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
@RequiredArgsConstructor
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ReturnInformation implements Listeners{

    private final TelegramBot telegramBot;

    public void send(DtoKeeper dtoKeeper){

        for (ReturnMessageInformation informations :
                dtoKeeper.getInformationList()) {
            SendMessage answer = new SendMessage(String.valueOf(informations.getChatId()), informations.getMessage());

            telegramBot.executeSendMessage(answer);
        }

    }

    public boolean support(String methodName){

        return methodName.equals("returnInformation");

    }
}
