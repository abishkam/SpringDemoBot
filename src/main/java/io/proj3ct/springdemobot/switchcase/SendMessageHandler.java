package io.proj3ct.springdemobot.switchcase;

import com.vdurmont.emoji.EmojiParser;
import io.proj3ct.springdemobot.model.Repeat;
import io.proj3ct.springdemobot.model.User;
import io.proj3ct.springdemobot.model.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import java.util.*;


@Service("/send ")
public class SendMessageHandler implements MessageHandler{

    private final UserRepository userRepository;

    public SendMessageHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Transactional
    public SendMessage send(Message mes){

            var textToSend = EmojiParser.parseToUnicode(mes.getText().substring(mes.getText().indexOf(" ")));
            Optional<User> user = userRepository.findById(mes.getChatId());
            if(user.isPresent()) user.get().getRepeat().add(new Repeat(mes.getMessageId(), textToSend));

            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(mes.getChatId()));
            message.setText(textToSend);

            return message;
    }

}
