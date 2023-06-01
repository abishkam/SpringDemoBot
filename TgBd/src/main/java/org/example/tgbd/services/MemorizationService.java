package org.example.tgbd.services;


import lombok.RequiredArgsConstructor;

import org.example.tgbd.dto.DtoKeeper;
import org.example.tgbd.dto.MemorizationDto;
import org.example.tgbd.mapper.BotMapper;
import org.example.tgbd.model.Memorization;
import org.example.tgbd.model.MemorizationRepository;
import org.example.tgbd.model.User;
import org.example.tgbd.model.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemorizationService {

    private final UserRepository userRepository;
    private final MemorizationRepository memorizationRepository;
    private final BotMapper botMapper;
    private final TimeService timeService;

    public String setMessageToEntity(DtoKeeper dtoKeeper) {

        Optional<User> user = userRepository.findById(dtoKeeper.getUserDto().getChatId());

        Memorization memorization = Memorization
            .builder()
            .messageId(dtoKeeper.getMemorizationDto().getMessageId())
            .message(dtoKeeper.getMemorizationDto().getMessage())
            .build();

        user.ifPresentOrElse(
            (value)
                -> {
                    memorization.setUser(value);
                    memorizationRepository.save(memorization);
            },
            ()
                -> {
                    User newUser = new User(dtoKeeper.getUserDto().getChatId(), dtoKeeper.getUserDto().getUserName(), Collections.singletonList(memorization));
                    userRepository.save(newUser);
            });

        return "Id of a message - " + dtoKeeper.getMemorizationDto().getMessageId();

    }

    public DtoKeeper getAllMessages(DtoKeeper dtoKeeper) {

        Optional<User> user = userRepository.findById(dtoKeeper.getUserDto().getChatId());

        if(user.isPresent()) {
            if (user.get().getMemorizations().isEmpty()) {
                dtoKeeper.setMessage("You don't have any saved messages");
            } else {
                List<MemorizationDto> memorizationDtos = botMapper.repeatMap(user.get().getMemorizations());
                dtoKeeper.setMessage("All your saved information");
                dtoKeeper.getUserDto().setMemorizationDtos(memorizationDtos);
            }
        } else {
            dtoKeeper.setMessage("You don't have any saved messages");
        }

        return dtoKeeper;
    }

    public DtoKeeper deletAndGetAllMessages(DtoKeeper dtoKeeper) {

        Optional<User> user = userRepository.findById(dtoKeeper.getUserDto().getChatId());
        timeService.deleteAllTimeOfMessage(dtoKeeper);
        memorizationRepository.deleteById(dtoKeeper.getUserDto().getMemorizationDtos().get(0).getMessageId());

        List<MemorizationDto> memorizationDtos = botMapper.repeatMap(user.get().getMemorizations());
        dtoKeeper.setMessage("All your saved information");
        dtoKeeper.getUserDto().setMemorizationDtos(memorizationDtos);

        return dtoKeeper;
    }

}
