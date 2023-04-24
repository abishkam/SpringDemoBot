package org.example.tgservice.handler;

import lombok.RequiredArgsConstructor;
import org.example.tgservice.config.UserInitialization;
import org.example.tgservice.keyboardMarkups.BaseButton;
import org.example.tgservice.patterns.HandlerTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service("number")
@RequiredArgsConstructor
public class NumbersMessageHandler implements MessageHandler{

    private final UserInitialization userInit;
    private final HandlerTemplate handlerTemplate;

    public SendMessage send(Message message) {

        ResponseEntity<String> response =
                handlerTemplate.timeResponse("setTimeToMemorization", userInit.getUserDto().getChatId().toString(), userInit.getUserDto().getState(), userInit.getUserDto().getTimeState(), String.valueOf(userInit.getUserDto().getAmount()));

        return new SendMessage(message.getChatId().toString(), "Вы сохранили сообщение");
    }


}
