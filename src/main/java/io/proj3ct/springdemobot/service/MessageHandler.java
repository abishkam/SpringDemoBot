package io.proj3ct.springdemobot.service;

import com.vdurmont.emoji.EmojiParser;
import io.proj3ct.springdemobot.model.Repeat;
import io.proj3ct.springdemobot.model.User;
import io.proj3ct.springdemobot.model.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.*;


@Service
public class MessageHandler {
    Timer timer;
    private final UserRepository userRepository;

    public MessageHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Transactional
    public SendMessage send(String messageText, Long chatId, Integer messageId){
            var textToSend = EmojiParser.parseToUnicode(messageText.substring(messageText.indexOf(" ")));
            Optional<User> user = userRepository.findById(chatId);
            Repeat repeat = new Repeat();
            repeat.setMessageId(messageId);
            repeat.setMessage(textToSend);
            user.get().getRepeat().add(repeat);
            System.out.println(user.get());
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));
            message.setText(textToSend);

            return message;
    }


}
