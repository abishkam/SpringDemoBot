package org.example.tgservice.keyboardMarkups;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class AddTimeButton implements Button{

    public InlineKeyboardMarkup inlineKeyboardMarkup(){
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        var minutes = new InlineKeyboardButton();
        minutes.setText("Минуты");
        minutes.setCallbackData("minute");

        var days = new InlineKeyboardButton();
        days.setText("Дни");
        days.setCallbackData("day");

        var months = new InlineKeyboardButton();
        months.setText("Месяцы");
        months.setCallbackData("month");

        List<InlineKeyboardButton> choice = new ArrayList<>(Arrays.asList(minutes, days, months));

        keyboardMarkup.setKeyboard(Collections.singletonList(choice));

        return keyboardMarkup;
    }

    @Override
    public EditMessageText edit(Message message) {

        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(message.getChatId().toString());
        editMessage.setText("Выберите единицу времени");
        editMessage.setReplyMarkup(inlineKeyboardMarkup());
        editMessage.setMessageId(message.getMessageId());
        return editMessage;
    }

    public boolean support(String callback){
        return callback.equals("Add_Time");
    }
}
