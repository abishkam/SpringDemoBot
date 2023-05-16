package org.example.tgservice.handler;

import lombok.RequiredArgsConstructor;
import org.example.tgservice.handler.interfaces.MessageHandler;
import org.example.tgservice.kafka.KafkaSender;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service("/getallmessages")
@RequiredArgsConstructor
public class GetAllMessagesHandler implements MessageHandler {

    private final KafkaSender kafkaSender;

    public void repeater(Message message) {
        kafkaSender(message);
    }

    @Override
    public void kafkaSender(Message mes) {

        Long chatId = mes.getChatId();
        String name = mes.getChat().getUserName();

        kafkaSender.memorizationResponse("getallmessages", chatId, "", "");

    }


    public boolean support(String message) {
        return message.equals("/getallmessages");
    }

}

