package org.example.tgbd.services;


import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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

        return "Вы сохранили сообщение";

    }

    public DtoKeeper getAllMessages(DtoKeeper dtoKeeper) {

        Optional<User> user = userRepository.findById(dtoKeeper.getUserDto().getChatId());

        if(user.isPresent()) {
            if (user.get().getMemorizations().isEmpty()) {
                dtoKeeper.setMessage("У вас нет сохраненной информации");
            } else {
                List<MemorizationDto> memorizationDtos = botMapper.repeatMap(user.get().getMemorizations());
                dtoKeeper.setMessage("Вся ваша сохранненая информация");
                dtoKeeper.getUserDto().setMemorizationDtos(memorizationDtos);
            }
        } else {
            dtoKeeper.setMessage("У вас нет сохраненной информации");
        }

        return dtoKeeper;
    }

    public DtoKeeper deletAndGetAllMessages(DtoKeeper dtoKeeper) {

        timeService.deleteAllTimeOfMessage(dtoKeeper);
        memorizationRepository.deleteById(dtoKeeper.getUserDto().getMemorizationDtos().get(0).getMessageId());

        List<MemorizationDto> memorizationDtos = botMapper.repeatMap(memorizationRepository.findAllByUserChatId(dtoKeeper.getUserDto().getChatId()));

        memorizationDtos.stream()
                .findAny()
                .ifPresentOrElse(
                        i -> {
                            dtoKeeper.setMessage("Вся ваша сохранненая информация");
                            dtoKeeper.getUserDto().setMemorizationDtos(memorizationDtos);
                        },
                        () -> dtoKeeper.setMessage("У вас нет сохраненной информации")
                );



        return dtoKeeper;
    }

}
