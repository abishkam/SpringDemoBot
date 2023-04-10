package org.example.tgservice;

import lombok.extern.slf4j.Slf4j;
import org.example.tgservice.handler.MessageHandler;
import org.example.tgservice.handler.SendMessageHandler;
import org.example.tgservice.patterns.BaseCommands;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;
import java.util.Timer;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {


    private final SendMessageHandler sendMessageHandler;
    private final Map<String, MessageHandler> messageHandlerMap;

    public TelegramBot(SendMessageHandler sendMessageHandler,
                       BaseCommands baseCommands,
                       Map<String, MessageHandler> messageHandlerMap) {
        super(System.getenv("bot.token"));
        this.sendMessageHandler = sendMessageHandler;
        this.messageHandlerMap = messageHandlerMap;
        try {
            this.execute(new SetMyCommands(baseCommands.getListOfCommands(), new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot,s command list: " + e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {

        messageHandlerMap.keySet().forEach(System.out::println);

        if (update.hasMessage() && update.getMessage().hasText()) {

            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            Timer timer = new Timer("Timer");

            MessageHandler messageHandler = messageHandlerMap.get(messageText.split(" ")[0]);
            try {
                execute(messageHandler.send(update.getMessage()));
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }

        }
    }


    @Override
    public String getBotUsername() {
        return System.getenv("bot.name");
    }

}