package org.example.tgservice.keyboardMarkups.timesButton;

import lombok.RequiredArgsConstructor;
import org.example.tgservice.kafka.KafkaSender;
import org.example.tgservice.keyboardMarkups.Button;
import org.example.tgservice.property.patterns.UnitOfTime;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Service
@RequiredArgsConstructor
public class HourButton implements Button {

    private final KafkaSender kafkaSender;

    @Override
    public boolean support(String callback) {
        return callback.startsWith("hour");
    }

    @Override
    public EditMessageText edit(CallbackQuery callbackQuery) {

        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(callbackQuery.getMessage().getChatId().toString());
        editMessage.setText("Напишите количество часов: ");
        editMessage.setMessageId(callbackQuery.getMessage().getMessageId());

        kafkaSender.timeResponse(
                "setUnitOfTime",
                callbackQuery.getMessage().getChatId(),
                Long.valueOf(
                        callbackQuery
                                .getData()
                                .split(" ")[1]
                ),
                UnitOfTime.HOURS
        );


        return editMessage;
    }

}
