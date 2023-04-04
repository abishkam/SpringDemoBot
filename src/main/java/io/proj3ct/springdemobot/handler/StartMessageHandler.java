package io.proj3ct.springdemobot.handler;

import com.vdurmont.emoji.EmojiParser;
import io.proj3ct.springdemobot.model.User;
import io.proj3ct.springdemobot.model.UserRepository;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service("/start")
public class StartMessageHandler implements MessageHandler {

    private final UserRepository userRepository;

    public StartMessageHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public SendMessage send(Message msg) {
        var chatId = msg.getChatId();
        var chat = msg.getChat();
        if (userRepository.findById(msg.getChatId()).isEmpty()) {
            User user = new User();
            user.setChatId(chatId);
            user.setUserName(chat.getUserName());
            userRepository.save(user);
            String answer = EmojiParser.parseToUnicode("Hi, " + chat.getUserName() + ", nice to meet you!" + "\uD83D\uDC12");
            return new SendMessage( String.valueOf(chatId), answer);
        }
        return new SendMessage( String.valueOf(chatId), "You are already registered");
    }

}
