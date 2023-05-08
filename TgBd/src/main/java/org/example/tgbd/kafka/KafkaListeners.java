package org.example.tgbd.kafka;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.example.tgbd.dto.DtoKeeper;
import org.example.tgbd.kafkacontroller.KafkaController;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class KafkaListeners {

    private final Map<String, KafkaController> kafkaControllerList;

    @KafkaListener(topics = "bdTopic", groupId = "myGroup")
    void listener(String data) {

        Gson jsonConverter = new Gson();
        DtoKeeper dtoKeeper = jsonConverter.fromJson(data, DtoKeeper.class);

        KafkaController controller =
            kafkaControllerList.entrySet().stream()
                    .filter(i -> i.getKey().equals(dtoKeeper.getClassName()))
                    .findFirst()
                    .map(i -> i.getValue())
                    .get();
        controller.request(dtoKeeper);


    }

}
