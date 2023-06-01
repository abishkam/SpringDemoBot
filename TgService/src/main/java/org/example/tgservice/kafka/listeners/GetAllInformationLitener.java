package org.example.tgservice.kafka.listeners;

import lombok.RequiredArgsConstructor;
import org.example.tgbd.dto.DtoKeeper;
import org.example.tgservice.TelegramBot;
import org.example.tgservice.keyboardMarkups.allMessagesButtons.AllInformation;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

@Service
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GetAllInformationLitener implements Listeners{

    private final TelegramBot telegramBot;
    private final AllInformation allInformation;

    public void send(DtoKeeper dtoKeeper){

        if(dtoKeeper.getMemorizationDto() != null){

            EditMessageText editMessage = new EditMessageText();
            editMessage.setChatId(dtoKeeper.getUserDto().getChatId().toString());
            editMessage.setText("All your saved information");
            editMessage.setReplyMarkup(
                    allInformation.inlineKeyboardMarkup(dtoKeeper)
            );
            editMessage.setMessageId(dtoKeeper.getMemorizationDto().getMessageId().intValue());

            telegramBot.executeEditMessageText(editMessage);
        } else{

            SendMessage answer = new SendMessage(String.valueOf(dtoKeeper.getUserDto().getChatId()),"All your saved information");
            answer.setReplyMarkup(allInformation.inlineKeyboardMarkup(dtoKeeper));
            telegramBot.executeSendMessage(answer);
        }

    }

    public boolean support(String methodName){
        return methodName.equals("getAllInformation");
    }
}
