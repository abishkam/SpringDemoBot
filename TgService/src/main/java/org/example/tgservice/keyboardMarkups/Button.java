package org.example.tgservice.keyboardMarkups;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface Button {

    boolean support(String callback);
    InlineKeyboardMarkup inlineKeyboardMarkup();
    EditMessageText edit(Message message);
}
