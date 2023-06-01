package org.example.tgservice.keyboardMarkups.allMessagesButtons;

import lombok.RequiredArgsConstructor;
import org.example.tgbd.dto.DtoKeeper;
import org.example.tgbd.dto.MemorizationDto;
import org.example.tgservice.keyboardMarkups.Button;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AllInformation implements Button {

    public InlineKeyboardMarkup inlineKeyboardMarkup(DtoKeeper dtoKeeper) {

        List<MemorizationDto> memList = dtoKeeper.getUserDto().getMemorizationDtos();

        InlineKeyboardMarkup markupLine =  new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        if(memList != null && memList.size() > 0){
            for (int i = 0; i < memList.size(); i++) {
                var message = new InlineKeyboardButton();
                message.setText(memList.get(i).getMessage());
                message.setCallbackData("on " + memList.get(i).getMessageId());
                rowsInLine.add(Collections.singletonList(message));
            }
        }

        markupLine.setKeyboard(rowsInLine);
        return markupLine;
    }

    @Override
    public boolean support(String callback) {
        return callback.split(" ")[1].equals("allInformation");
    }

    @Override
    public EditMessageText edit(CallbackQuery callbackQuery) {

        return new EditMessageText();
    }
}
