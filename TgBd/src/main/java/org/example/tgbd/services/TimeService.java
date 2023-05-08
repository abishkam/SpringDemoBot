package org.example.tgbd.services;

import lombok.RequiredArgsConstructor;
import org.example.tgbd.dto.DtoKeeper;
import org.example.tgbd.dto.UserDto;
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


    public void setTime(final DtoKeeper dtoKeeper) {

        Optional<Memorization> memorization
                = memorizationRepository
                .findById(Long.valueOf(dtoKeeper.getUserDto().getState()));


        if (memorization.isPresent()) {

            if (memorization.get().getTimeList().stream()
                    .anyMatch(i -> !(i.getAmount() == dtoKeeper.getUserDto().getAmount())
                            && !(i.getUnitOfTime() == dtoKeeper.getUserDto().getTimeState()))) {
                Time time = new Time();
                time.setUnitOfTime(dtoKeeper.getUserDto().getTimeState());
                time.setAmount(dtoKeeper.getUserDto().getAmount());
                time.setMemorization(memorization.get());

                timeRepository.save(time);
            }

        }

    }

    public Boolean checkPresenceTime(UserDto userDto) {
        Optional<Memorization> memorization = memorizationRepository.findById(Long.valueOf(userDto.getState()));

        return memorization.get().getTimeList().stream()
                .anyMatch(i -> (i.getAmount() == userDto.getAmount()) && (i.getUnitOfTime() == userDto.getTimeState()));

    }
}
