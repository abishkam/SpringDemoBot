package org.example.tgservice.property;

import lombok.RequiredArgsConstructor;
import org.example.tgservice.kafka.config.AppPropConfig;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TgBotInfo {

    private final AppPropConfig config;

    public String helpMessage() {
        return config.botInfo().getMessage("help.message", null, null);
    }

}
