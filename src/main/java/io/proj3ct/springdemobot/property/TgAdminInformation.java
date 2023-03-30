package io.proj3ct.springdemobot.property;

import io.proj3ct.springdemobot.config.AppPropConfig;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class TgAdminInformation {
    private final AppPropConfig config;

    public TgAdminInformation(AppPropConfig config){
        this.config=config;
    }

    public String token(){
        return config.adminInformation().getMessage("bot.token", null, Locale.ENGLISH);
    }
    public String name(){
        return config.adminInformation().getMessage("bot.name", null, Locale.ENGLISH);
    }
    public Long owner(){
        return Long.parseLong(config.adminInformation().getMessage("bot.owner", null, Locale.ENGLISH));
    }
}
