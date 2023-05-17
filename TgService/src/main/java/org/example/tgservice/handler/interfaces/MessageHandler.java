package org.example.tgservice.handler.interfaces;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageHandler {

    void repeater(Message message);

    boolean support(String str);

    void kafkaSender(Message message);

}
