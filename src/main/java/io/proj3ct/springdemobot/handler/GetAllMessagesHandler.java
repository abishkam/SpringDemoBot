package io.proj3ct.springdemobot.handler;

import io.proj3ct.springdemobot.DTO.RepeatDTO;
import io.proj3ct.springdemobot.mapper.BotMapper;
import io.proj3ct.springdemobot.model.UserRepository;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.stream.Collectors;

@Service("/getallmessages")
public class GetAllMessagesHandler implements MessageHandler{

    private final UserRepository userRepository;
    private final BotMapper botMapper;

    public GetAllMessagesHandler(UserRepository userRepository, BotMapper botMapper) {
        this.userRepository = userRepository;
        this.botMapper = botMapper;
    }

    @Override
    public SendMessage send(Message message) {
        List<RepeatDTO> repeatDTOS = botMapper.map(userRepository.findById(message.getChatId()).get().getRepeat());

        if(repeatDTOS.isEmpty()){
            return new SendMessage(String.valueOf(message.getChatId()), "You don't have any stored messages");
        }

        String allMessages = repeatDTOS.stream().map(i -> i.getMessageId() +" "+i.getMessage()).collect(Collectors.joining("\n"));
        return new SendMessage(String.valueOf(message.getChatId()), allMessages);
    }

}

