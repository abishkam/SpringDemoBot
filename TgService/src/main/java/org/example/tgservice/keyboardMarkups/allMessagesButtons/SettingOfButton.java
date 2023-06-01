package org.example.tgservice.keyboardMarkups.allMessagesButtons;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class SettingOfButton implements Button {

    public InlineKeyboardMarkup inlineKeyboardMarkup(String id) {

        InlineKeyboardMarkup markupLine = new InlineKeyboardMarkup();

        var back = new InlineKeyboardButton();
        back.setText("\uE235");
        back.setCallbackData("kafka allInformation");

        var deleteMessage = new InlineKeyboardButton();
        deleteMessage.setText("\u274C");
        deleteMessage.setCallbackData("kafka deleteMessage " + id);

        var addTime = new InlineKeyboardButton();
        addTime.setText("\u23F3");
        addTime.setCallbackData("kafka time " + id);

        List<InlineKeyboardButton> choice = new ArrayList<>(Arrays.asList(back, deleteMessage, addTime));

        markupLine.setKeyboard(Collections.singletonList(choice));

        return markupLine;
    }

    @Override
    public boolean support(String callback) {
        return callback.split(" ")[0].equals("on");
    }

    @Override
    public EditMessageText edit(CallbackQuery callbackQuery) {

        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(callbackQuery.getMessage().getChatId().toString());
        editMessage.setText("Действия над сообщением");
        editMessage.setReplyMarkup(
                inlineKeyboardMarkup(callbackQuery.getData().split(" ")[1])
        );

        editMessage.setMessageId(callbackQuery.getMessage().getMessageId());

        return editMessage;
    }
}
