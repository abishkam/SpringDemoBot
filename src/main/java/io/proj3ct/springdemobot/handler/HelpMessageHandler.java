package io.proj3ct.springdemobot.handler;

import io.proj3ct.springdemobot.property.TgBotInfo;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service("/help")
public class HelpMessageHandler implements MessageHandler{

    private final TgBotInfo information;

    public HelpMessageHandler(TgBotInfo information) {
        this.information = information;
    }

    @Override
    public SendMessage send(Message message) {
        return new SendMessage(String.valueOf((message.getChatId())), information.helpMessage());
    }
}
