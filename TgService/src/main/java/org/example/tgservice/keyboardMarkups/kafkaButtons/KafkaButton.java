package org.example.tgservice.keyboardMarkups.kafkaButtons;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface KafkaButton {

    void kafkaHandler(Update update);

    boolean support(String string);
}