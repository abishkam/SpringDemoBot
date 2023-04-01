package io.proj3ct.springdemobot.service;

import io.proj3ct.springdemobot.model.User;
import io.proj3ct.springdemobot.model.UserRepository;
import io.proj3ct.springdemobot.patterns.BaseCommands;
import io.proj3ct.springdemobot.property.TgAdminInformation;
import io.proj3ct.springdemobot.property.TgBotInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.Timestamp;
import java.util.*;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {



    private final UserRepository userRepository;
    private final TgAdminInformation tgAdminInformation;
    private final TgBotInfo tgBotInfo;
    private final MessageHandler messageHandler;
    private final BaseCommands baseCommands;
    //private final BotMapper botMapper;

    public TelegramBot(UserRepository userRepository, TgAdminInformation tgAdminInformation, MessageHandler messageHandler,TgBotInfo tgBotInfo, BaseCommands baseCommands /*,BotMapper botMapper*/){
        super(tgAdminInformation.token());
        this.userRepository = userRepository;
        this.tgAdminInformation = tgAdminInformation;
        this.messageHandler = messageHandler;
        this.tgBotInfo = tgBotInfo;
        this.baseCommands = baseCommands;
        //this.botMapper = botMapper;
        try{
            this.execute(new SetMyCommands(baseCommands.getListOfCommands(), new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot,s command list: " + e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage() && update.getMessage().hasText()){

            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            Timer timer = new Timer("Timer");

            class RemindTask extends TimerTask {
                public void run() {
                    try {
                        execute(messageHandler.send(messageText, chatId, update.getMessage().getMessageId()));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }

                }
            }

            if(messageText.contains("/send ")){
                System.out.println(chatId);
                Optional<User> user = userRepository.findById(chatId);
                messageHandler.send(messageText, chatId, update.getMessage().getMessageId());
                System.out.println(userRepository.findById(chatId).get());
            }

            switch(messageText){
                case "/start":
                    registerUser(update.getMessage());
                    break;
            }
        }
    }



    private void registerUser(Message msg) {
        if(userRepository.findById(msg.getChatId()).isEmpty()){
            var chatid = msg.getChatId();
            var chat = msg.getChat();
            User user = new User();
            user.setChatId(chatid);
            user.setUserName(chat.getUserName());
            user.setRegisteredAt(new Timestamp(System.currentTimeMillis()));
            userRepository.save(user);
            log.info("user saved " + user);
        }
    }

    public void addNewTimer(int count, String dayOrMonth){

        if(dayOrMonth.toLowerCase().contains("day") && !dayOrMonth.toLowerCase().contains("month")){

        }
    }

    @Override
    public String getBotUsername() {
        return tgAdminInformation.name();
    }

}
