package org.example.tgservice.keyboardMarkups.timesButton;

import org.example.tgservice.keyboardMarkups.Button;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class AddTimeButton implements Button {

    public InlineKeyboardMarkup inlineKeyboardMarkup(Long messageId) {

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        var hours = new InlineKeyboardButton();
        hours.setText("Часы");
        hours.setCallbackData("hour " + messageId);

        var days = new InlineKeyboardButton();
        days.setText("Дни");
        days.setCallbackData("day "+ messageId);

        var months = new InlineKeyboardButton();
        months.setText("Месяцы");
        months.setCallbackData("month "+ messageId);

        List<InlineKeyboardButton> choice = new ArrayList<>(Arrays.asList(hours, days, months));

        keyboardMarkup.setKeyboard(Collections.singletonList(choice));

        return keyboardMarkup;
    }

    @Override
    public EditMessageText edit(CallbackQuery callbackQuery) {

        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(callbackQuery.getMessage().getChatId().toString());
        editMessage.setText("Выберите единицу времени");
        editMessage.setReplyMarkup(
                inlineKeyboardMarkup(
                Long.valueOf(
                        callbackQuery
                        .getData()
                        .split(" ")[1]
                )));

        editMessage.setMessageId(callbackQuery.getMessage().getMessageId());

        return editMessage;
    }

    public boolean support(String callback){
        return callback.startsWith("Add_Time");
    }
}
