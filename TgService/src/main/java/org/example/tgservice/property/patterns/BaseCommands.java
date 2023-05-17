package org.example.tgservice.property.patterns;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

@Component
public class BaseCommands {
    private final List<BotCommand> listOfCommands;

    private BaseCommands(List<BotCommand> listOfCommands) {
        this.listOfCommands = listOfCommands;
        listOfCommands.add(new BotCommand("/start","get a welcome message"));
        listOfCommands.add(new BotCommand("/help","info how to use this bot"));
        listOfCommands.add(new BotCommand("/getallmessages","get all your messages with id"));
    }

    public List<BotCommand> getListOfCommands() {
        return listOfCommands;
    }

}
