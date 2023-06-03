package org.example.tgbd.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tgbd.dto.DtoKeeper;
import org.example.tgbd.dto.ReturnMessageInformation;
import org.example.tgbd.dto.TimeDto;
import org.example.tgbd.mapper.BotMapper;
import org.example.tgbd.model.Memorization;
import org.example.tgbd.model.MemorizationRepository;
import org.example.tgbd.model.Time;
import org.example.tgbd.model.TimeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TimeService {

    private final MemorizationRepository memorizationRepository;
    private final TimeRepository timeRepository;
    private final BotMapper botMapper;

    public void setUnitOfTime(final DtoKeeper dtoKeeper) {

        if (memorizationRepository
                .existsByUserChatId(dtoKeeper.getUserDto().getChatId(),
                        dtoKeeper.getMemorizationDto().getMessageId())) {

            Memorization memorization
                    = memorizationRepository.findById(dtoKeeper.getMemorizationDto().getMessageId()).get();

            Time time = Time.builder()
                    .unitOfTime(dtoKeeper.getTimeDto().getUnitOfTime())
                    .memorization(memorization)
                    .build();

            timeRepository.save(time);

        }

    }

    public void setAmountOfTime(DtoKeeper dtoKeeper) {

        Time time = timeRepository.findTimeByMemorizationMessageIdAndFlag(dtoKeeper.getUserDto().getChatId());
        time.setAmount(dtoKeeper.getTimeDto().getAmount());
        Instant timeToRepeatInstant = Instant.now();
        LocalDateTime dateTime = LocalDateTime.ofInstant(timeToRepeatInstant, ZoneId.systemDefault());
        dateTime = dateTime.plus(time.getAmount(),
                ChronoUnit.valueOf(time.getUnitOfTime().toUpperCase()));
        timeToRepeatInstant = dateTime.atZone(ZoneId.systemDefault()).toInstant();

        Calendar timeToRepeatCalendar = Calendar.getInstance();

        timeToRepeatCalendar.setTime(Date.from(timeToRepeatInstant));
        timeToRepeatCalendar.set(Calendar.MINUTE, 0);
        timeToRepeatCalendar.set(Calendar.SECOND, 0);
        timeToRepeatCalendar.set(Calendar.MILLISECOND, 0);

        time.setDateToReturn(timeToRepeatCalendar.getTime());

        dtoKeeper.setTimeDto(botMapper.timeModelToDto(time));
        dtoKeeper.setMemorizationDto(botMapper.memorizationModelToDto(time.getMemorization()));
        dtoKeeper.setMessage(dtoKeeper.getMemorizationDto().getMessage());

        timeRepository.save(time);

    }

    public void deleteTime(DtoKeeper dtoKeeper) {
        timeRepository.deleteById(dtoKeeper.getTimeDto().getId());

    }

    public void deleteAllTimeOfMessage(DtoKeeper dtoKeeper){
        timeRepository.deleteAllByMessageId(dtoKeeper.getUserDto().getMemorizationDtos().get(0).getMessageId());
    }


    public DtoKeeper getListOfTimeOfMessage(DtoKeeper dtoKeeper) {

        List<TimeDto> timeDtos = botMapper.timeDtoMap(
                timeRepository.findAllByMemorizationMessageId(
                        dtoKeeper
                                .getUserDto()
                                .getMemorizationDtos()
                                .get(0)
                                .getMessageId()
                ));

        dtoKeeper
                .getUserDto()
                .getMemorizationDtos()
                .get(0)
                .setTimeList(timeDtos);

        return dtoKeeper;
    }

    public DtoKeeper getListOfTimeAndMessages(Date date1, Date date2){

        List<Time> timeList = timeRepository.findAllByDateToReturnBetween(date1, date2);
        DtoKeeper dtoKeeper = new DtoKeeper();
        dtoKeeper.setInformationList(new ArrayList<>());
        for (Time time : timeList) {
            dtoKeeper.getInformationList().add(
                    new ReturnMessageInformation(
                            time.getMemorization().getUser().getChatId(),
                            time.getMemorization().getMessage()
                    ));
            timeRepository.deleteById(time.getId());
        }
        dtoKeeper.setMethodName("returnInformation");

        return dtoKeeper;
    }

}
