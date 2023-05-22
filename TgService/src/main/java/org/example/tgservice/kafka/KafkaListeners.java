package org.example.tgservice.kafka;

import lombok.RequiredArgsConstructor;
import org.example.tgbd.dto.DtoKeeper;
import org.example.tgservice.TelegramBot;
import org.example.tgservice.keyboardMarkups.allMessagesButtons.AllInformation;
import org.example.tgservice.keyboardMarkups.BaseButton;
import org.example.tgservice.keyboardMarkups.timesButton.TimeListAttachedToTheInformation;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;


@Component
@RequiredArgsConstructor
public class KafkaListeners {

    private final BaseButton baseButton;
    private final TelegramBot telegramBot;
    private final AllInformation allInformation;
    private final TimeListAttachedToTheInformation timeList;

    @KafkaListener(topics = "serviceTopic", groupId = "myGroup")
    void listener(DtoKeeper dtoKeeper) {

        if(dtoKeeper.getMethodName() != null && dtoKeeper.getMethodName().equals("getAllInformation")){

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
        } else if (dtoKeeper.getMethodName() != null && dtoKeeper.getMethodName().equals("getListOfTimeOfMessage")) {

            EditMessageText editMessage = new EditMessageText();
            editMessage.setChatId(dtoKeeper.getUserDto().getChatId().toString());
            editMessage.setText("Time list attached to the information");
            editMessage.setReplyMarkup(
                    timeList.inlineKeyboardMarkup(dtoKeeper)
            );

            editMessage.setMessageId(dtoKeeper.getMemorizationDto().getMessageId().intValue());

            telegramBot.executeEditMessageText(editMessage);
        } else{

            SendMessage answer = new SendMessage(String.valueOf(dtoKeeper.getUserDto().getChatId()), dtoKeeper.getMessage());

            if(dtoKeeper.getMethodName() != null && dtoKeeper.getMethodName().equals("setInformation")) {

                answer.setReplyMarkup(baseButton.inlineKeyboardMarkup(dtoKeeper.getMemorizationDto().getMessageId()));

            }

            telegramBot.executeSendMessage(answer);
        }


    }

}
