package org.example.tgservice.handler;

import org.example.tgbd.dto.RepeatDto;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("/getallmessages")

public class GetAllMessagesHandler implements MessageHandler {

//    private final UserRepository userRepository;

    public GetAllMessagesHandler() {
    }

    @Override
    public SendMessage send(Message message) {
        List<RepeatDto> repeatDtos = new ArrayList<>();

        //todo REST Template with Dto
//                = botMapper.map(userRepository.findById(message.getChatId()).get().getRepeat());

        if (repeatDtos.isEmpty()) {
            return new SendMessage(String.valueOf(message.getChatId()), "You don't have any stored messages");
        }

        String allMessages =
                repeatDtos
                        .stream()
                        .map(i -> i.getMessageId() + " " + i.getMessage())
                        .collect(Collectors.joining("\n"));
        return new SendMessage(String.valueOf(message.getChatId()), allMessages);
    }

}

