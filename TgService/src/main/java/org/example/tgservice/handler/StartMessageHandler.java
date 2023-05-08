package org.example.tgservice.handler;

import lombok.RequiredArgsConstructor;
import org.example.tgservice.handler.interfaces.MessageHandler;
import org.example.tgservice.kafka.KafkaSender;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service("/start")
@RequiredArgsConstructor
public class StartMessageHandler implements MessageHandler {

    private final KafkaSender kafkaSender;

    public void repeater(Message message) {
        kafkaSender(message);
    }

    public void kafkaSender(Message msg) {
        var chatId = msg.getChatId();
        var chat = msg.getChat();

        kafkaSender.userResponse("createUser", chatId.toString(), chat.getUserName());

    }

    public boolean support(String message) {
        return message.equals("/start");
    }

}
