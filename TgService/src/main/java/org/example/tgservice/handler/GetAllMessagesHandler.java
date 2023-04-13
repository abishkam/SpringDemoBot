package org.example.tgservice.handler;

import lombok.RequiredArgsConstructor;
import org.example.tgservice.patterns.HandlerTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Objects;

@Service("/getallmessages")
@RequiredArgsConstructor
public class GetAllMessagesHandler implements MessageHandler {

    private final HandlerTemplate handlerTemplate;

    @Override
    public SendMessage send(Message mes) {

        String chatId = String.valueOf(mes.getChatId());
        String name = mes.getChat().getUserName();

        ResponseEntity<String> allMessages = handlerTemplate.createResponse("getAllMessages", chatId, name);

        return new SendMessage(chatId, Objects.requireNonNull(allMessages.getBody()));
    }

}

