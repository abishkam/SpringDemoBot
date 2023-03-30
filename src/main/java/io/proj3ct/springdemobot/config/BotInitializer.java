package io.proj3ct.springdemobot.config;

import io.proj3ct.springdemobot.property.TgAdminInformation;
import io.proj3ct.springdemobot.service.TelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Component
public class BotInitializer {
    private final TelegramBot telegramBot;
    private final TgAdminInformation tgAdminInformation;

    public BotInitializer(TelegramBot telegramBot, TgAdminInformation tgAdminInformation){
        this.telegramBot=telegramBot;
        this.tgAdminInformation=tgAdminInformation;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try{
            telegramBotsApi.registerBot(telegramBot);
        }catch (TelegramApiException e){
            log.error("Error occurred" + e.getMessage());
        }
    }

}
