package io.proj3ct.springdemobot.patterns;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

@Component
public class BaseCommands {
    List<BotCommand> listOfCommands;

    public BaseCommands(List<BotCommand> listOfCommands) {
        this.listOfCommands = listOfCommands;
        listOfCommands.add(new BotCommand("/start","get a welcome message"));
        listOfCommands.add(new BotCommand("/mydata", "get your data stored"));
        listOfCommands.add(new BotCommand("/deletedata", "delete my data"));
        listOfCommands.add(new BotCommand("/help","info how to use this bot"));
        listOfCommands.add(new BotCommand("/settings","set your preferences"));
    }

    public List<BotCommand> getListOfCommands() {
        return listOfCommands;
    }

    public void setListOfCommands(List<BotCommand> listOfCommands) {
        this.listOfCommands = listOfCommands;
    }

}
