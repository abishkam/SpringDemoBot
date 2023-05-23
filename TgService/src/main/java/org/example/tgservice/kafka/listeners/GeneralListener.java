package org.example.tgservice.kafka.listeners;


import lombok.RequiredArgsConstructor;
import org.example.tgbd.dto.DtoKeeper;
import org.example.tgservice.TelegramBot;
import org.example.tgservice.keyboardMarkups.BaseButton;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
@RequiredArgsConstructor
@Order(Ordered.LOWEST_PRECEDENCE)
public class GeneralListener implements Listeners{

    private final TelegramBot telegramBot;
    private final BaseButton baseButton;

    public void send(DtoKeeper dtoKeeper){
        SendMessage answer = new SendMessage(String.valueOf(dtoKeeper.getUserDto().getChatId()), dtoKeeper.getMessage());

        if(dtoKeeper.getMethodName() != null && dtoKeeper.getMethodName().equals("setInformation")) {

            answer.setReplyMarkup(baseButton.inlineKeyboardMarkup(dtoKeeper.getMemorizationDto().getMessageId()));

        }

        telegramBot.executeSendMessage(answer);
    }

    public boolean support(String methodName){
        return true;
    }
}
