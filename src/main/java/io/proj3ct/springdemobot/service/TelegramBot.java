package io.proj3ct.springdemobot.service;

import com.vdurmont.emoji.EmojiParser;
import io.proj3ct.springdemobot.model.User;
import io.proj3ct.springdemobot.model.UserRepository;
import io.proj3ct.springdemobot.property.TgAdminInformation;
import io.proj3ct.springdemobot.property.TgBotInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    private final UserRepository userRepository;
    private final TgAdminInformation tgAdminInformation;
    private final TgBotInfo tgBotInfo;
    private final MessageHandler messageHandler;

    public TelegramBot(UserRepository userRepository, TgAdminInformation tgAdminInformation, MessageHandler messageHandler,TgBotInfo tgBotInfo){
        super(tgAdminInformation.token());
        this.userRepository = userRepository;
        this.tgAdminInformation = tgAdminInformation;
        this.messageHandler = messageHandler;
        this.tgBotInfo = tgBotInfo;
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start","get a welcome message"));
        listOfCommands.add(new BotCommand("/mydata", "get your data stored"));
        listOfCommands.add(new BotCommand("/deletedata", "delete my data"));
        listOfCommands.add(new BotCommand("/help","info how to use this bot"));
        listOfCommands.add(new BotCommand("/settings","set your preferences"));
        try{
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot,s command list: " + e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            if(messageText.contains("/send ")){
                messageHandler.send(messageText, chatId);
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

    private void startCommandReceived(long chatId, String name){

        String answer = EmojiParser.parseToUnicode("Hi, " + name + ", nice to meet you!" + "\uD83D\uDC12");
        log.info("Replied to user " + name);
        sendMessage(chatId, answer);

    }

    public void sendMessage(long chatId, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("weather");
        row.add("get random joke");
        keyboardRows.add(row);
        row = new KeyboardRow();
        row.add("register");
        row.add("check_my_data");
        row.add("delete_my_data");
        keyboardRows.add(row);
        keyboardMarkup.setKeyboard(keyboardRows);
        message.setReplyMarkup(keyboardMarkup);
        try {
            execute(message);
        }catch (TelegramApiException e){
            log.error("Error occurred" + e.getMessage());
        }

    }

    private void executeEditMessageText(String text, long chatId, long messageId){
        EditMessageText message = new EditMessageText();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.setMessageId((int) messageId);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return tgAdminInformation.name();
    }

}


//    private void register(long chatId) {
//        SendMessage message = new SendMessage();
//        message.setChatId(String.valueOf(chatId));
//        message.setText("Do you really want to register?");
//
//        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
//        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
//        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
//        var yesButton = new InlineKeyboardButton();
//
//        yesButton.setText("Yes");
//        yesButton.setCallbackData(tgBotInfo.yesButton());
//
//        var noButton = new InlineKeyboardButton();
//
//        noButton.setText("No");
//        noButton.setCallbackData(tgBotInfo.noButton());
//
//        rowInLine.add(yesButton);
//        rowInLine.add(noButton);
//
//        rowsInLine.add(rowInLine);
//
//        markupInLine.setKeyboard(rowsInLine);
//        message.setReplyMarkup(markupInLine);
//
//        try {
//            execute(message);
//        } catch (TelegramApiException e) {
//            throw new RuntimeException(e);
//        }
//    }


//@Override
//    public void onUpdateReceived(Update update) {
//        if(update.hasMessage() && update.getMessage().hasText()){
//            String messageText = update.getMessage().getText();
//            long chatId = update.getMessage().getChatId();
//            if(messageText.contains("/send ")){
//                messageHandler.send(messageText, chatId);
//            }
//            switch(messageText){
//                case "/start":
//                    registerUser(update.getMessage());
//                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
//                    break;
//                case "/help":
//                    sendMessage(chatId, tgBotInfo.welcomMessage());
//                    break;
//                case "/register":
//                    register(chatId);
//                    break;
//                    default: sendMessage(chatId, "Sorry, command was not recognized.");
//            }
//        } else if (update.hasCallbackQuery()) {
//            String callbackData = update.getCallbackQuery().getData();
//            long messageId = update.getCallbackQuery().getMessage().getMessageId();
//            long chatId = update.getCallbackQuery().getMessage().getChatId();
//
//            if(callbackData.equals(tgBotInfo.yesButton())){
//                String text = "You pressed YES button";
//                executeEditMessageText(text, chatId, messageId);
//            }
//            else if(callbackData.equals(tgBotInfo.noButton())){
//                String text = "You pressed NO button";
//                executeEditMessageText(text, chatId, messageId);
//            }
//        }
//    }