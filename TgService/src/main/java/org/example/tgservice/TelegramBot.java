package org.example.tgservice;

import lombok.extern.slf4j.Slf4j;
import org.example.tgservice.config.UserInitialization;
import org.example.tgservice.handler.MessageHandler;
import org.example.tgservice.keyboardMarkups.Button;
import org.example.tgservice.patterns.BaseCommands;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    private final Map<String, MessageHandler> messageHandlerMap;
    private final List<Button> buttons;
    private final UserInitialization userInit;

    public TelegramBot(BaseCommands baseCommands,
                       Map<String, MessageHandler> messageHandlerMap,
                       List<Button> buttons, UserInitialization userInit) {
        super(System.getenv("bot.token"));
        this.messageHandlerMap = messageHandlerMap;
        this.buttons = buttons;
        this.userInit = userInit;
        try {
            this.execute(new SetMyCommands(baseCommands.getListOfCommands(), new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot,s command list: " + e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage()){
            if (update.getMessage().getText().startsWith("/")) {

                MessageHandler messageHandler = messageHandlerMap.get(update.getMessage().getText().split(" ")[0]);

                executeSendMessage(messageHandler.send(update.getMessage()));
                if(update.getMessage().getText().equals("/start")){
                    userInit.findUser(update.getMessage().getChatId());
                }
            }

            if ( userInit.getUserDto().getState().equals("\\d+") &&
                    !update.getMessage().getText().trim().matches("\\d+")) {

                executeSendMessage(new SendMessage(update.getMessage().getChatId().toString(), "Введите число"));

            } else if (!userInit.getUserDto().getState().equals("free") && update.getMessage().getText().matches("\\d+")) {

                userInit.getUserDto().setAmount(Short.parseShort(update.getMessage().getText()));
                executeSendMessage(messageHandlerMap.get("number").send(update.getMessage()));
                userInit.getUserDto().setState("free");
            }
        }

        if(update.hasCallbackQuery()) {

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

    public void executeSendMessage(SendMessage message){
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
