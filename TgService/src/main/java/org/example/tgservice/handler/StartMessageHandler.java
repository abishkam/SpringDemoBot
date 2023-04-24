package org.example.tgservice.handler;

import lombok.RequiredArgsConstructor;
import org.example.tgservice.patterns.HandlerTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Objects;

@Service("/start")
@RequiredArgsConstructor
public class StartMessageHandler implements MessageHandler {

    private final HandlerTemplate handlerTemplate;

    public SendMessage send(Message msg) {
        var chatId = msg.getChatId();
        var chat = msg.getChat();

        ResponseEntity<String> response =  handlerTemplate.userResponse("createUser", chatId.toString(), chat.getUserName());

        return new SendMessage(String.valueOf(chatId), Objects.requireNonNull(response.getBody()));

    }

}
