package io.proj3ct.springdemobot.property;

import io.proj3ct.springdemobot.config.AppPropConfig;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class TgBotInfo {
    private final AppPropConfig config;

    public TgBotInfo(AppPropConfig config){
        this.config=config;
    }

    public String helpMessage(){
        return config.botInfo().getMessage("help.message", null, Locale.ENGLISH);
    }

}
