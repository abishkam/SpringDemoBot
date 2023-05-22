package org.example.tgservice.keyboardMarkups;

import lombok.RequiredArgsConstructor;
import org.example.tgservice.keyboardMarkups.kafkaButtons.KafkaButton;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ButtonHandler {

    private final List<KafkaButton> kafkaButtonList;

    public void handler(Update update) {

        KafkaButton kafkaButton = kafkaButtonList
                .stream()
                .filter(i -> i.support(update.getCallbackQuery().getData()))
                .findFirst()
                .get();

        kafkaButton.kafkaHandler(update);

    }
}
