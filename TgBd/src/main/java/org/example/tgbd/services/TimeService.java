package org.example.tgbd.services;

import lombok.RequiredArgsConstructor;
import org.example.tgbd.dto.TimeDto;
import org.example.tgbd.model.Memorization;
import org.example.tgbd.model.MemorizationRepository;
import org.example.tgbd.model.Time;
import org.example.tgbd.model.TimeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TimeService {

    private final MemorizationRepository memorizationRepository;
    private final TimeRepository timeRepository;


    public void setTime(final Long chatId,
                          final Long messageId,
                          final TimeDto timeDto){

        Optional<Memorization> memorization = memorizationRepository.findById(messageId);

        if(memorization.isPresent()){
            Time time = new Time();
            time.setUnitOfTime(timeDto.getUnitOfTime());
            time.setAmount(timeDto.getAmount());
            time.setMemorization(memorization.get());

            timeRepository.save(time);
        }

    }
}
