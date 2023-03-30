package io.proj3ct.springdemobot.service;

import com.vdurmont.emoji.EmojiParser;
import io.proj3ct.springdemobot.model.Repeat;
import io.proj3ct.springdemobot.model.User;
import io.proj3ct.springdemobot.model.UserRepository;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.Optional;

@Service
public class MessageHandler {
    private final UserRepository userRepository;

    public MessageHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void send(String messageText, Long chatId){
        var textToSend = EmojiParser.parseToUnicode(messageText.substring(messageText.indexOf(" ")));
        Optional<User> user = userRepository.findById(chatId);
        user.get().getRepeat().add(new Repeat(textToSend, new Timestamp(System.currentTimeMillis())));
    }


}
