package org.example.tgservice.handler;

import com.vdurmont.emoji.EmojiParser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.tgservice.config.UserInitialization;
import org.example.tgservice.keyboardMarkups.BaseButton;
import org.example.tgservice.patterns.HandlerTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;


import java.util.Objects;

@Service("/send")
@RequiredArgsConstructor
public class SendMessageHandler implements MessageHandler {

    private final HandlerTemplate handlerTemplate;
    private final BaseButton baseMarkup;
    private final UserInitialization userInit;

    @Transactional
    public SendMessage send(Message mes) {

        String chatId = String.valueOf(mes.getChatId());
        String name = mes.getChat().getUserName();
        String messageId = String.valueOf(mes.getMessageId());
        String message = mes.getText();


        if (mes.getText().trim().equals("/send")) {
            return new SendMessage(chatId, "You didn't write message");
        } else {

            var textToSend = EmojiParser.parseToUnicode(message.substring(mes.getText().indexOf(" ")));

            ResponseEntity<String> response = handlerTemplate.memorizationResponse("setMessage", chatId, name, messageId, textToSend);

            userInit.getUserDto().setState(messageId);

            SendMessage answer = new SendMessage(chatId, Objects.requireNonNull(response.getBody()));
            answer.setReplyMarkup(baseMarkup.inlineKeyboardMarkup());

            return answer;
        }

    }

}
