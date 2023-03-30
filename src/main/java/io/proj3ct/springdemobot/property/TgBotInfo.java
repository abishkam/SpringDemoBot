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

    public String welcomMessage(){
        return config.botInfo().getMessage("welcome.message", null, Locale.ENGLISH);
    }
    public String yesButton(){
        return config.botInfo().getMessage("yes.button", null, Locale.ENGLISH);
    }
    public String noButton(){
        return config.botInfo().getMessage("no.button", null, Locale.ENGLISH);
    }
}
