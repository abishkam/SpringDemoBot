package org.example.tgservice.handler;

import com.vdurmont.emoji.EmojiParser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service("/send")
@RequiredArgsConstructor
public class SendMessageHandler implements MessageHandler {

    private final RestTemplate restTemplate = new RestTemplate();

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
            String url = String.format("http://localhost:8080/user/%s/%s/%s/%s", chatId, name, messageId, textToSend);
            ResponseEntity<Void> response = restTemplate.getForEntity(url, Void.class);

            return new SendMessage(chatId, "Id of a message - " + messageId);
        }

    }

}
