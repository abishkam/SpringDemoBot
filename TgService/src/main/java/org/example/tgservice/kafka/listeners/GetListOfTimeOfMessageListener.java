package org.example.tgservice.kafka.listeners;

import lombok.RequiredArgsConstructor;
import org.example.tgbd.dto.DtoKeeper;
import org.example.tgservice.TelegramBot;
import org.example.tgservice.keyboardMarkups.timesButton.TimeListAttachedToTheInformation;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

@Service
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GetListOfTimeOfMessageListener implements Listeners{

    private final TelegramBot telegramBot;
    private final TimeListAttachedToTheInformation timeList;

    public void send(DtoKeeper dtoKeeper){

        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(dtoKeeper.getUserDto().getChatId().toString());
        editMessage.setText("Time list attached to the information");
        editMessage.setReplyMarkup(
                timeList.inlineKeyboardMarkup(dtoKeeper)
        );

        editMessage.setMessageId(dtoKeeper.getMemorizationDto().getMessageId().intValue());

        telegramBot.executeEditMessageText(editMessage);
    }

    public boolean support(String methodName){
        return methodName.equals("getListOfTimeOfMessage");
    }
}
