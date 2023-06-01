package org.example.tgservice.keyboardMarkups.kafkaButtons;

import lombok.RequiredArgsConstructor;
import org.example.tgservice.kafka.KafkaSender;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class DeleteTime implements KafkaButton{

    private final KafkaSender kafkaSender;

    @Override
    public void kafkaHandler(Update update) {
        kafkaSender.timeResponse("deleteTime",
                update.getCallbackQuery().getMessage().getChatId(),
                Long.parseLong(update.getCallbackQuery().getData().split(" ")[3]),
                Long.parseLong(update.getCallbackQuery().getData().split(" ")[4]),
                Long.parseLong(update.getCallbackQuery().getData().split(" ")[5]));
    }

    @Override
    public boolean support(String callback) {
        return callback.split(" ")[1].equals("deleteTime");
    }

}
