package org.example.tgservice.handler;

import lombok.RequiredArgsConstructor;
import org.example.tgservice.handler.interfaces.MessageHandler;
import org.example.tgservice.kafka.KafkaSender;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service("number")
@RequiredArgsConstructor
public class NumbersMessageHandler implements MessageHandler {

    private final KafkaSender kafkaSender;

    public void repeater(Message message) {
        kafkaSender(message);
    }

    public void kafkaSender(Message message) {

       if (message.getText().matches("\\d+")) {

//            userInit.getUserDto().setAmount(Short.parseShort(message.getText()));
//            kafkaSender.timeResponse("setTimeToMemorization",
//                    userInit.getUserDto().getState(),
//                    userInit.getUserDto().getTimeState(),
//                    String.valueOf(userInit.getUserDto().getAmount()));
//            userInit.getUserDto().setState("free");

       } else {
           kafkaSender.patternResponse("notNumber", message.getChatId(),"Введите число");
       }

    }

    public boolean support(String message) {
        return message.matches("\\d+");
    }

}
