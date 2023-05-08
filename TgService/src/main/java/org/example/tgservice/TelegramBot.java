package org.example.tgservice;

import lombok.extern.slf4j.Slf4j;
import org.example.tgservice.config.UserInitialization;
import org.example.tgservice.handler.CommandHandler;
import org.example.tgservice.keyboardMarkups.Button;
import org.example.tgservice.property.patterns.BaseCommands;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.util.List;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    private final List<Button> buttons;
    private final CommandHandler commandHandler;
    private final UserInitialization userInitialization;

    public TelegramBot(BaseCommands baseCommands,
                       List<Button> buttons,
                       CommandHandler commandHandler, UserInitialization userInitialization) {
        super(System.getenv("bot.token"));
        this.buttons = buttons;
        this.commandHandler = commandHandler;
        this.userInitialization = userInitialization;
        try {
            this.execute(new SetMyCommands(baseCommands.getListOfCommands(), new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot,s command list: " + e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.getMessage().getText().equals("/start")) {
            userInitialization.findUser(update.getMessage().getChatId());
        }

        if (update.hasMessage() && update.getMessage().hasText()) {

            commandHandler.handler(update.getMessage());

        }

        if (update.hasCallbackQuery()) {

            Button button = buttons.stream()
                    .filter(i -> i.support(update.getCallbackQuery().getData()))
                    .findFirst()
                    .get();

            executeEditMessageText(button.edit(update.getCallbackQuery().getMessage()));

        }

    }

    @Override
    public String getBotUsername() {
        return System.getenv("bot.name");
    }

    public void executeSendMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void executeEditMessageText(EditMessageText message){
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

}
