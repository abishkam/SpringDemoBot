package io.proj3ct.springdemobot.service;

import io.proj3ct.springdemobot.model.UserRepository;
import io.proj3ct.springdemobot.patterns.BaseCommands;
import io.proj3ct.springdemobot.property.TgAdminInformation;
import io.proj3ct.springdemobot.property.TgBotInfo;
import io.proj3ct.springdemobot.switchcase.MessageHandler;
import io.proj3ct.springdemobot.switchcase.SendMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    private final UserRepository userRepository;
    private final TgAdminInformation tgAdminInformation;
    private final TgBotInfo tgBotInfo;
    private final SendMessageHandler sendMessageHandler;
    private final BaseCommands baseCommands;
    private final Map<String, MessageHandler> messageHandlerMap;

    public TelegramBot(UserRepository userRepository, TgAdminInformation tgAdminInformation, SendMessageHandler sendMessageHandler, TgBotInfo tgBotInfo, BaseCommands baseCommands, Map<String, MessageHandler> messageHandlerMap){
        super(tgAdminInformation.token());
        this.userRepository = userRepository;
        this.tgAdminInformation = tgAdminInformation;
        this.sendMessageHandler = sendMessageHandler;
        this.tgBotInfo = tgBotInfo;
        this.baseCommands = baseCommands;
        this.messageHandlerMap = messageHandlerMap;
        try{
            this.execute(new SetMyCommands(baseCommands.getListOfCommands(), new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot,s command list: " + e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {

        messageHandlerMap.keySet().forEach(System.out::println);



        if(update.hasMessage() && update.getMessage().hasText()){

            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            Timer timer = new Timer("Timer");

            class RemindTask extends TimerTask {
                public void run() {
                    try {
                        execute(sendMessageHandler.send(update.getMessage()));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            MessageHandler a = messageHandlerMap.get(messageText.substring(0,6));
            try {
                execute(a.send(update.getMessage()));
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }

        }
    }


    @Override
    public String getBotUsername() {
        return tgAdminInformation.name();
    }

}
