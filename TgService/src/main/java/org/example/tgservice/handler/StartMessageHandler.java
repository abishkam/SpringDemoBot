package org.example.tgservice.handler;

import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import org.example.tgservice.patterns.HandlerTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Objects;

@Service("/start")
@RequiredArgsConstructor
public class StartMessageHandler implements MessageHandler {

    private final HandlerTemplate handlerTemplate = new HandlerTemplate();

    public SendMessage send(Message msg) {
        var chatId = msg.getChatId();
        var chat = msg.getChat();

        ResponseEntity<Boolean> response =
                (ResponseEntity<Boolean>) handlerTemplate.createResponse(true,"createUser", chatId.toString(), chat.getUserName());

        Boolean answerRest = Objects.requireNonNull(response.getBody());

        if (answerRest) {
            String answer =
                    EmojiParser
                            .parseToUnicode("Hi, " + chat.getUserName() + ", nice to meet you!");
            return new SendMessage(String.valueOf(chatId), answer);
        }
        return new SendMessage(String.valueOf(chatId), "You are already registered");
    }

}
