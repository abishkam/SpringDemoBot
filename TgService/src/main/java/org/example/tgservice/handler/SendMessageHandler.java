package org.example.tgservice.handler;

import lombok.RequiredArgsConstructor;
import org.example.tgservice.handler.interfaces.MessageHandler;
import org.example.tgservice.kafka.KafkaSender;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service("/send")
@RequiredArgsConstructor
public class SendMessageHandler implements MessageHandler {

    private final KafkaSender kafkaSender;

    public void repeater(Message message) {
        kafkaSender(message);
    }

    public void kafkaSender(Message mes) {

        Long chatId = mes.getChatId();
        Integer messageId = mes.getMessageId();
        String message = mes.getText();


        if (mes.getText().trim().equals("/send")) {
            kafkaSender.patternResponse("emptyMessage",  chatId,"You didn't write message");
        } else {

            var textToSend = message.substring(mes.getText().indexOf(" "));

            kafkaSender.memorizationResponse("setInformation", chatId, messageId, textToSend);

        }
    }

    public boolean support(String message) {
        return message.equals("/send");
    }

}
