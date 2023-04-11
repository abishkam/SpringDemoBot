package org.example.tgservice.handler;

import lombok.RequiredArgsConstructor;
import org.example.tgbd.dto.RepeatDto;
import org.example.tgservice.patterns.HandlerTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service("/getallmessages")
@RequiredArgsConstructor
public class GetAllMessagesHandler implements MessageHandler {

    private final HandlerTemplate handlerTemplate = new HandlerTemplate();

    @Override
    public SendMessage send(Message mes) {

        String chatId = String.valueOf(mes.getChatId());
        String name = mes.getChat().getUserName();

        ResponseEntity<String> allMessages =
                (ResponseEntity<String>) handlerTemplate.createResponse(new String(),"getAllMessages", chatId, name);

        return new SendMessage(chatId, Objects.requireNonNull(allMessages.getBody()));
    }

}

