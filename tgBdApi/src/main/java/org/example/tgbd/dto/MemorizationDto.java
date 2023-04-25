package org.example.tgbd.dto;

import lombok.Data;
import org.example.tgbd.queue.Queue;

import java.util.Comparator;
import java.util.List;


@Data
public class MemorizationDto {

    private Long messageId;
    private String message;
    private List<TimeDto> timeList;
    private Queue queue;

    public void setQueue(){
        queue = new Queue(timeList.size());
        timeList.stream().sorted(Comparator.comparingInt(TimeDto::getAmount));

        for (TimeDto timeDto:
                timeList) {
            queue.enqueue(timeDto);
        }
    }

    public TimeDto peek(){
        return queue.peek();
    }

}
