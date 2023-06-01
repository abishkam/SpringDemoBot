package org.example.tgservice.handler;

import lombok.RequiredArgsConstructor;
import org.example.tgservice.handler.interfaces.MessageHandler;
import org.example.tgservice.kafka.KafkaSender;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service("setAmountOfTime")
@RequiredArgsConstructor
public class NumbersMessageHandler implements MessageHandler {

    private final KafkaSender kafkaSender;

    public void repeater(Message message) {
        kafkaSender(message);
    }

    public void kafkaSender(Message message) {
       if (message.getText().matches("\\d+")) {
            kafkaSender.timeResponse("setAmountOfTime",
                    message.getChatId(),
                    Short.parseShort(message.getText()));
       }

    }

    public boolean support(String message) {
        return message.matches("\\d+");
    }


}
