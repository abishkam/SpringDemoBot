package org.example.tgservice.keyboardMarkups.timesButton;

import lombok.RequiredArgsConstructor;
import org.example.tgservice.kafka.KafkaSender;
import org.example.tgservice.keyboardMarkups.Button;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Service
@RequiredArgsConstructor
public class MinuteButton implements Button {

    private final KafkaSender kafkaSender;

    @Override
    public boolean support(String callback) {
        return callback.equals("minute");
    }

    @Override
    public InlineKeyboardMarkup inlineKeyboardMarkup() {
        return null;
    }

    @Override
    public EditMessageText edit(Message message) {

        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(message.getChatId().toString());
        editMessage.setText("Введите количество минут: ");
        editMessage.setMessageId(message.getMessageId());

        kafkaSender.timeResponse("setTimeToMemorization", message.getChatId(), message.getMessageId(), "MINUTES");

        return editMessage;
    }

}
