package org.example.tgservice.handler;

import lombok.RequiredArgsConstructor;
import org.example.tgservice.property.TgBotInfo;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service("/help")
@RequiredArgsConstructor
public class HelpMessageHandler implements MessageHandler {

    private final TgBotInfo information;

    @Override
    public SendMessage send(Message message) {
        return new SendMessage(String.valueOf((message.getChatId())), information.helpMessage());
    }
}
