package org.example.tgservice.kafka;

import lombok.RequiredArgsConstructor;
import org.example.tgbd.dto.DtoKeeper;
import org.example.tgservice.TelegramBot;
import org.example.tgservice.keyboardMarkups.BaseButton;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;


@Component
@RequiredArgsConstructor
public class KafkaListeners {

    private final BaseButton baseMarkup;
    private final TelegramBot telegramBot;

    @KafkaListener(topics = "serviceTopic", groupId = "myGroup")
    void listener(DtoKeeper dtoKeeper) {
        System.out.println(dtoKeeper);
        SendMessage answer = new SendMessage(String.valueOf(dtoKeeper.getUserDto().getChatId()), dtoKeeper.getMessage());

        if(dtoKeeper.getMethodName() != null && dtoKeeper.getMethodName().equals("setInformation")) {
            answer.setReplyMarkup(baseMarkup.inlineKeyboardMarkup());
        }

        telegramBot.executeSendMessage(answer);

    }

}
