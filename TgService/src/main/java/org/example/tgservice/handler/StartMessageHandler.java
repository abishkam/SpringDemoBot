package org.example.tgservice.handler;

import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import org.example.tgbd.model.User;
import org.example.tgbd.model.UserRepository;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service("/start")
@RequiredArgsConstructor
public class StartMessageHandler implements MessageHandler {

    private final UserRepository userRepository;

    public SendMessage send(Message msg) {
        var chatId = msg.getChatId();
        var chat = msg.getChat();
        if (userRepository.findById(msg.getChatId()).isEmpty()) {
            User user = new User();
            user.setChatId(chatId);
            user.setUserName(chat.getUserName());
            userRepository.save(user);
            String answer =
                    EmojiParser
                            .parseToUnicode("Hi, " + chat.getUserName() + ", nice to meet you!");
            return new SendMessage( String.valueOf(chatId), answer);
        }
        return new SendMessage( String.valueOf(chatId), "You are already registered");
    }

}
