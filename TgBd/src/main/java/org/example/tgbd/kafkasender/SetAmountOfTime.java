package org.example.tgbd.kafkasender;

import lombok.RequiredArgsConstructor;
import org.example.tgbd.dto.DtoKeeper;
import org.example.tgbd.kafka.TopicProperties;
import org.example.tgbd.services.TimeService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Component("setAmountOfTime")
@RequiredArgsConstructor
public class SetAmountOfTime implements KafkaSender {

    private final TimeService timeService;
    private final KafkaTemplate<String, DtoKeeper> kafkaTemplate;
    private final TopicProperties topicProperties;

    @Override
    public void send(DtoKeeper dtoKeeper) {

        dtoKeeper = timeService.setAmountOfTime(dtoKeeper);
        scheduler(dtoKeeper);

    }

    public void scheduler(DtoKeeper dtoKeeper){
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                kafkaTemplate.send(topicProperties.getTopic(), dtoKeeper);
                timeService.deleteTime(dtoKeeper);
            }
        };

        Timer timer = new Timer();

        timer.schedule(timerTask, Date.from(Instant.now().plus(
                dtoKeeper.getTimeDto().getAmount(),
                ChronoUnit.valueOf(dtoKeeper.getTimeDto().getUnitOfTime()))));
    }
}