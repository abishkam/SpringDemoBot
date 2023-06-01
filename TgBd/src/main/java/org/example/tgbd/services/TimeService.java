package org.example.tgbd.services;

import lombok.RequiredArgsConstructor;
import org.example.tgbd.dto.DtoKeeper;
import org.example.tgbd.dto.TimeDto;
import org.example.tgbd.mapper.BotMapper;
import org.example.tgbd.model.Memorization;
import org.example.tgbd.model.MemorizationRepository;
import org.example.tgbd.model.Time;
import org.example.tgbd.model.TimeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
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

    public DtoKeeper setAmountOfTime(DtoKeeper dtoKeeper) {

        Time time = timeRepository.findTimeByMemorizationMessageIdAndFlag(dtoKeeper.getUserDto().getChatId());
        time.setAmount(dtoKeeper.getTimeDto().getAmount());

        Instant a = Instant.now().plus(time.getAmount(),
                ChronoUnit.valueOf(time.getUnitOfTime()));
        time.setDateToReturn(Date.from(a));

        dtoKeeper.setTimeDto(botMapper.timeModelToDto(time));
        dtoKeeper.setMemorizationDto(botMapper.memorizationModelToDto(time.getMemorization()));
        dtoKeeper.setMessage(dtoKeeper.getMemorizationDto().getMessage());

        timeRepository.save(time);

        return dtoKeeper;
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
}
