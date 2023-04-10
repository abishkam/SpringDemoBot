package org.example.tgservice.handler;

import com.vdurmont.emoji.EmojiParser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service("/send")
@RequiredArgsConstructor
public class SendMessageHandler implements MessageHandler {


    @Transactional
    public SendMessage send(Message mes) {

        String chatId = String.valueOf(mes.getChatId());
        System.out.println(chatId);
        if ( mes.getText().equals("/send") || mes.getText().equals("/send ") ) {
            return new SendMessage(chatId, "You didn't write message");
        } else {
            var textToSend = EmojiParser.parseToUnicode(mes.getText().substring(mes.getText().indexOf(" ")));
            //todo rest Template with UserDto
//            Optional<UserDto> user = userRepository.findById(mes.getChatId());
//            user.ifPresent(value -> value.getRepeat().add(new Repeat(Long.valueOf(mes.getMessageId()), textToSend)));

            return new SendMessage(chatId, "Id of a message - " + mes.getMessageId());
        }

    }

}
