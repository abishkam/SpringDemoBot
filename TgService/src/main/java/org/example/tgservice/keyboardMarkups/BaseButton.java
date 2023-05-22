package org.example.tgservice.keyboardMarkups;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BaseButton implements Button {

    public InlineKeyboardMarkup inlineKeyboardMarkup(Long messageId) {

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        var createdTime = new InlineKeyboardButton();
        createdTime.setText("Добавить время");
        createdTime.setCallbackData("Add_Time " + messageId);

        List<InlineKeyboardButton> choice = new ArrayList<>(Collections.singletonList(createdTime));

        keyboardMarkup.setKeyboard(Collections.singletonList(choice));

        return keyboardMarkup;
    }

    @Override
    public EditMessageText edit(CallbackQuery callbackQuery) {
        return null;
    }

    public boolean support(String callback) {
        return callback.equals("BaseButton");
    }
}
