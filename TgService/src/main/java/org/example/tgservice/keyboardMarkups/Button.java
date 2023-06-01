package org.example.tgservice.keyboardMarkups;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface Button {

    boolean support(String callback);
    EditMessageText edit(CallbackQuery callbackQuery);
}
