package org.example.tgservice.draft;

import org.example.tgbd.dto.MemorizationDto;
import org.example.tgbd.dto.TimeDto;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@EnableScheduling
@Service
public class DynamicSchedulingConfigTest implements SchedulingConfigurer {


    private List<MemorizationDto> memoList = new ArrayList<>();
    Queue queue = new Queue(2);

    {
        TimeDto timeDto = new TimeDto();
        timeDto.setUnitOfTime("MINUTES");
        timeDto.setAmount((short) 1);

        TimeDto timeDto2 = new TimeDto();
        timeDto2.setUnitOfTime("MINUTES");
        timeDto2.setAmount((short) 2);

        TimeDto timeDto7 = new TimeDto();
        timeDto7.setUnitOfTime("MINUTES");
        timeDto7.setAmount((short) 70);

        MemorizationDto memDto = new MemorizationDto();
        memDto.setMessage("privet");
        memDto.setTimeList(Arrays.asList(timeDto, timeDto2, timeDto7));
        memDto.setQueue();

        TimeDto timeDto3 = new TimeDto();
        timeDto3.setUnitOfTime("MINUTES");
        timeDto3.setAmount((short) 5);

        TimeDto timeDto4 = new TimeDto();
        timeDto4.setUnitOfTime("MINUTES");
        timeDto4.setAmount((short) 10);

        MemorizationDto memDto2 = new MemorizationDto();
        memDto2.setMessage("privet");
        memDto2.setTimeList(Arrays.asList(timeDto3, timeDto4));
        memDto2.setQueue();

        memoList.add(memDto);
        memoList.add(memDto2);

    }

    public Executor taskExecutor() {
        return Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());



        //Почему новая задача не выполняет все TimeDto#getAmount
        for (int i = 0; i < 2; i++) {
            int finalI = i;
            taskRegistrar.addTriggerTask(
                    () -> {
                        System.out.println(finalI+ " " + new Date());
                        //tick.tick(finalI);
                    },
                    triggerContext -> {
                        Instant time = null;
                        Optional<Instant> lastCompletionTime =
                                Optional.ofNullable(triggerContext.lastCompletion());
                        TimeDto a = memoList.get(finalI).peek();
                        time = lastCompletionTime
                                .orElseGet(Instant::now)
                                .plus(a.getAmount(), ChronoUnit.SECONDS);

                        return time;
                    }
            );
        }

    }


}

