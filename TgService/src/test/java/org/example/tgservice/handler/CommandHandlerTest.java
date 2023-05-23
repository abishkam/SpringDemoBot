package org.example.tgservice.handler;

import org.example.tgservice.config.AppPropConfig;
import org.example.tgservice.handler.interfaces.MessageHandler;
import org.example.tgservice.kafka.KafkaSender;
import org.example.tgservice.property.TgBotInfo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class CommandHandlerTest {

    private static CommandHandler handler;
    private static List<MessageHandler> list;

    /**
     * Тест на MessageHandler.
     */
    @BeforeAll
    static void init() {
        KafkaSender kafkaSender = new KafkaSender(null, null);

        list = new ArrayList<>();

        MessageHandler handler1 = new GetAllMessagesHandler(kafkaSender);
        list.add(handler1);

        MessageHandler handler2 = new HelpMessageHandler(new TgBotInfo(new AppPropConfig()), kafkaSender);
        list.add(handler2);

        MessageHandler handler3 = new NumbersMessageHandler(kafkaSender);
        list.add(handler3);

        MessageHandler handler4 = new SendMessageHandler(kafkaSender);
        list.add(handler4);

        MessageHandler handler5 = new StartMessageHandler(kafkaSender);
        list.add(handler5);


        handler = new CommandHandler(list, new NumbersMessageHandler(kafkaSender));
    }

    @Test
    void handler(){
        String command = "/help";
        MessageHandler messageHandler = list.stream()
                .filter(i -> i.support(command))
                .findFirst()
                .get();

        String actualClassFullName = messageHandler.getClass().getName();
        String[] actualName = actualClassFullName.split("\\.");
        assertEquals("HelpMessageHandler", actualName[actualName.length - 1]);
    }

    @Test
    void handler2(){
        String command = "/getallmessages";
        MessageHandler messageHandler = list.stream()
                .filter(i -> i.support(command))
                .findFirst()
                .get();

        String actualClassFullName = messageHandler.getClass().getName();
        String[] actualName = actualClassFullName.split("\\.");
        assertEquals("GetAllMessagesHandler", actualName[actualName.length - 1]);
    }

    //todo ПАДАЕТ
    @Test
    void handler3(){
        String command = "какой-то текст";
        List<MessageHandler> collect = list.stream()
                .filter(i -> i.support(command))
                .collect(Collectors.toList());
        assertEquals(1, collect.size());
    }
}