package org.example.tgservice.kafka.listeners;


import lombok.RequiredArgsConstructor;
import org.example.tgbd.dto.DtoKeeper;
import org.example.tgservice.TelegramBot;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
@RequiredArgsConstructor
@Order(Ordered.LOWEST_PRECEDENCE)
public class GeneralListener implements Listeners {

    private final TelegramBot telegramBot;

    public void send(DtoKeeper dtoKeeper) {
        SendMessage answer = new SendMessage(String.valueOf(dtoKeeper.getUserDto().getChatId()), dtoKeeper.getMessage());

        telegramBot.executeSendMessage(answer);
    }

    public boolean support(String methodName) {
        return true;
    }

}
