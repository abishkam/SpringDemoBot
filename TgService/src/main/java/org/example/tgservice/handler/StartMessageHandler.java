package org.example.tgservice.handler;

import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Objects;

@Service("/start")
@RequiredArgsConstructor
public class StartMessageHandler implements MessageHandler {

    private final RestTemplate restTemplate = new RestTemplate();

    public SendMessage send(Message msg) {
        var chatId = msg.getChatId();
        var chat = msg.getChat();

        String url = String.format("http://localhost:PORT//user/%s/%s", chatId, chat.getUserName());
        ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);
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
