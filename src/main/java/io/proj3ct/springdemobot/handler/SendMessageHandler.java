package io.proj3ct.springdemobot.handler;

import com.vdurmont.emoji.EmojiParser;
import io.proj3ct.springdemobot.model.Repeat;
import io.proj3ct.springdemobot.model.User;
import io.proj3ct.springdemobot.model.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import java.util.*;


@Service("/send")
public class SendMessageHandler implements MessageHandler{

    private final UserRepository userRepository;

    public SendMessageHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Transactional
    public SendMessage send(Message mes){

        String chatId = String.valueOf(mes.getChatId());
        System.out.println(chatId);
        if(mes.getText().equals("/send") || mes.getText().equals("/send ") ){
            return new SendMessage(chatId, "You didn't write message");
        }else{
            var textToSend = EmojiParser.parseToUnicode(mes.getText().substring(mes.getText().indexOf(" ")));
            Optional<User> user = userRepository.findById(mes.getChatId());
            user.ifPresent(value -> value.getRepeat().add(new Repeat(Long.valueOf(mes.getMessageId()), textToSend)));

            return new SendMessage(chatId, "Id of a message - " + mes.getMessageId());
        }

    }

}
