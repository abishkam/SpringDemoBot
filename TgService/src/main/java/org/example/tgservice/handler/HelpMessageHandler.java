package org.example.tgservice.handler;

import lombok.RequiredArgsConstructor;
import org.example.tgservice.handler.interfaces.MessageHandler;
import org.example.tgservice.kafka.KafkaSender;
import org.example.tgservice.property.TgBotInfo;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service("/help")
@RequiredArgsConstructor
public class HelpMessageHandler implements MessageHandler {

    private final TgBotInfo information;
    private final KafkaSender kafkaSender;

    public void repeater(Message message) {
        kafkaSender(message);
    }

    public void kafkaSender(Message message) {
        kafkaSender.patternResponse("helpMessage", information.helpMessage());
    }

    public boolean support(String message) {
        return message.equals("/help");
    }



}
