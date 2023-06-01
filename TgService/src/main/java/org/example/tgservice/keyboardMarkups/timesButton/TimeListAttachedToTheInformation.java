package org.example.tgservice.keyboardMarkups.timesButton;

import lombok.RequiredArgsConstructor;
import org.example.tgbd.dto.DtoKeeper;
import org.example.tgbd.dto.MemorizationDto;
import org.example.tgbd.dto.TimeDto;
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
public class TimeListAttachedToTheInformation implements Button {

    public InlineKeyboardMarkup inlineKeyboardMarkup(DtoKeeper dtoKeeper) {

        MemorizationDto memorizationDto = dtoKeeper.getUserDto().getMemorizationDtos().get(0);
        List<TimeDto> timeList = memorizationDto.getTimeList();

        InlineKeyboardMarkup markupLine =  new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();

        for (int i = 0; i < timeList.size(); i++) {
            var message = new InlineKeyboardButton();
            message.setText(timeList.get(i).getDateToReturn().toString());
            message.setCallbackData("onTime " + memorizationDto.getMessageId() + " " +timeList.get(i).getId());
            rowsInLine.add(Collections.singletonList(message));
        }

        var back = new InlineKeyboardButton();
        back.setText("\uE235");
        back.setCallbackData("kafka allInformation");

        var addTime = new InlineKeyboardButton();
        addTime.setText("Добавить время");
        addTime.setCallbackData("Add_Time " + memorizationDto.getMessageId());

        rowsInLine.add(Arrays.asList(back, addTime));

        markupLine.setKeyboard(rowsInLine);
        return markupLine;
    }

    @Override
    public boolean support(String callback) {
        return callback.startsWith("time");
    }

    @Override
    public EditMessageText edit(CallbackQuery callbackQuery) {
        return null;
    }
}
