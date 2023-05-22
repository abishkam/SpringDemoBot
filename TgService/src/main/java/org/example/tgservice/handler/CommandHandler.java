package org.example.tgservice.handler;

import lombok.RequiredArgsConstructor;
import org.example.tgservice.handler.interfaces.MessageHandler;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@RequiredArgsConstructor
@Service("command")
public class CommandHandler {

    private final List<MessageHandler> messageHandlerList;
    private final NumbersMessageHandler numbersMessageHandler;

    public void handler(Message message) {

        if (message.getText().startsWith("/")) {
            String command = message.getText().split(" ")[0];
            MessageHandler messageHandler = messageHandlerList.stream()
                    .filter(i -> i.support(command))
                    .findFirst()
                    .get();

            messageHandler.repeater(message);

        }else{
            numbersMessageHandler.repeater(message);
        }

    }

}
