package org.example.tgservice.keyboardMarkups;

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
public class TimeUnit implements Button {

    public InlineKeyboardMarkup inlineKeyboardMarkup(String callback) {

        String messageId = callback.split(" ")[1];
        String timeId = callback.split(" ")[2];
        String messageIdToReturn = callback.split(" ")[3];
        String chatId = callback.split(" ")[4];

        InlineKeyboardMarkup markupLine = new InlineKeyboardMarkup();

        var back = new InlineKeyboardButton();
        back.setText("\uE235");
        back.setCallbackData("kafka allInformation");

        var deleteTime = new InlineKeyboardButton();
        deleteTime.setText("\u274C");
        deleteTime.setCallbackData("kafka deleteTime " + chatId + " "+messageIdToReturn + " " +messageId + " " + timeId);

        List<InlineKeyboardButton> choice = new ArrayList<>(Arrays.asList(back, deleteTime));

        markupLine.setKeyboard(Collections.singletonList(choice));

        return markupLine;
    }

    @Override
    public boolean support(String callback) {
        return callback.startsWith("onTime");
    }

    @Override
    public EditMessageText edit(CallbackQuery callbackQuery) {

        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(callbackQuery.getMessage().getChatId().toString());
        editMessage.setText("Действия над сообщением");
        editMessage.setReplyMarkup(
                inlineKeyboardMarkup(callbackQuery.getData() + " " +callbackQuery.getMessage().getMessageId() +" " +callbackQuery.getMessage().getChatId())
        );
        editMessage.setMessageId(callbackQuery.getMessage().getMessageId());

        return editMessage;
    }
}
