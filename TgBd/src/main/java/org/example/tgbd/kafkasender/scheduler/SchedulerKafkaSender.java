package org.example.tgbd.kafkasender.scheduler;

import lombok.RequiredArgsConstructor;
import org.example.tgbd.dto.DtoKeeper;
import org.example.tgbd.kafka.TopicProperties;
import org.example.tgbd.services.TimeService;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@RequiredArgsConstructor
@Configuration
@EnableScheduling
public class SchedulerKafkaSender {

    private final TimeService timeService;
    private final KafkaTemplate<String, DtoKeeper> kafkaTemplate;
    private final TopicProperties topicProperties;

    @Scheduled(cron = "0 * * * * ?")
    private void MessageSender(){

        Date now1 = Date.from(Instant.now().minus(1, ChronoUnit.MINUTES));
        Date now2 = Date.from(Instant.now().plus(1, ChronoUnit.MINUTES));

        DtoKeeper dtoKeeper = timeService.getListOfTimeAndMessages(now1,now2);

        if (dtoKeeper.getInformationList().size()>0){
            kafkaTemplate.send(topicProperties.getServiceTopic(), dtoKeeper);
        }

    }

}