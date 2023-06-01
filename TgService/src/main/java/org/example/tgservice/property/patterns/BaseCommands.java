package org.example.tgservice.property.patterns;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.ArrayList;
import java.util.List;

@Component
public class BaseCommands {
    private final List<BotCommand> listOfCommands;

    private BaseCommands() {
        this.listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start","registration"));
        listOfCommands.add(new BotCommand("/help","info how to use this bot"));
        listOfCommands.add(new BotCommand("/getallmessages","get all your information"));
    }

    public List<BotCommand> getListOfCommands() {
        return listOfCommands;
    }

}
