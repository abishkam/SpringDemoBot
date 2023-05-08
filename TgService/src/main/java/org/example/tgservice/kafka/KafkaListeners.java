package org.example.tgservice.kafka;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.example.tgbd.dto.DtoKeeper;
import org.example.tgservice.TelegramBot;
import org.example.tgservice.config.UserInitialization;
import org.example.tgservice.keyboardMarkups.BaseButton;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;


@Component
@RequiredArgsConstructor
public class KafkaListeners {

    private final UserInitialization userInitialization;
    private final BaseButton baseMarkup;
    private final TelegramBot telegramBot;

    @KafkaListener(topics = "serviceTopic", groupId = "myGroup")
    void listener(String data) {

        Gson a = new Gson();
        DtoKeeper dtoKeeper = a.fromJson(data, DtoKeeper.class);

        if (dtoKeeper.getMethodName() != null && dtoKeeper.getMethodName().equals("getById")) {
            userInitialization.setUserDto(dtoKeeper.getUserDto());
        } else if(dtoKeeper.getMethodName() != null && dtoKeeper.getMethodName().equals("setInformation")) {
            userInitialization.getUserDto().setState(String.valueOf(dtoKeeper.getMemorizationDto().getMessageId()));
            SendMessage answer = new SendMessage(String.valueOf(userInitialization.getUserDto().getChatId()), dtoKeeper.getMessage());
            answer.setReplyMarkup(baseMarkup.inlineKeyboardMarkup());
            telegramBot.executeSendMessage(answer);
        } else if(dtoKeeper.getMessage() != null && userInitialization.getUserDto() != null){
            telegramBot.executeSendMessage(new SendMessage(String.valueOf(userInitialization.getUserDto().getChatId()), dtoKeeper.getMessage()));
        }

    }

}
