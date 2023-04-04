package io.proj3ct.springdemobot.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageHandler{

    SendMessage send(Message message);

}
