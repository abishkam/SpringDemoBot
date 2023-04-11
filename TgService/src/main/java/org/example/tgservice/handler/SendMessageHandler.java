package org.example.tgservice.handler;

import com.vdurmont.emoji.EmojiParser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.tgservice.patterns.HandlerTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service("/send")
@RequiredArgsConstructor
public class SendMessageHandler implements MessageHandler {

    private final HandlerTemplate handlerTemplate = new HandlerTemplate();

    @Transactional
    public SendMessage send(Message mes) {

        String chatId = String.valueOf(mes.getChatId());
        String name = mes.getChat().getUserName();
        String messageId = String.valueOf(mes.getMessageId());
        String message = mes.getText();


        if ( mes.getText().equals("/send") || mes.getText().equals("/send ") ) {
            return new SendMessage(chatId, "You didn't write message");
        } else {

            var textToSend = EmojiParser.parseToUnicode(message.substring(mes.getText().indexOf(" ")));

            ResponseEntity<String> allMessages =
                    (ResponseEntity<String>) handlerTemplate.createResponse(new String(),"setMessage", chatId, name, messageId, textToSend);

            return new SendMessage(chatId, "Id of a message - " + messageId);
        }

    }

}
