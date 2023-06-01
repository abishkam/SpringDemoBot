package org.example.tgservice.keyboardMarkups.kafkaButtons;

import lombok.RequiredArgsConstructor;
import org.example.tgservice.kafka.KafkaSender;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class GetListOfTimeOfMessage implements KafkaButton {

    private final KafkaSender kafkaSender;

    @Override
    public void kafkaHandler(Update update) {
        kafkaSender.timeResponse("getListOfTimeOfMessage",
                update.getCallbackQuery().getMessage().getChatId(),
                update.getCallbackQuery().getMessage().getMessageId().longValue(),
                Long.parseLong(update.getCallbackQuery().getData().split(" ")[2]));
    }

    @Override
    public boolean support(String callback) {
        return callback.split(" ")[1].equals("time");
    }
}
