package org.example.tgbd.services;

import lombok.RequiredArgsConstructor;
import org.example.tgbd.dto.RepeatDto;
import org.example.tgbd.mapper.BotMapper;
import org.example.tgbd.model.Repeat;
import org.example.tgbd.model.User;
import org.example.tgbd.model.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BotMapper botMapper;

    public Boolean createNewUser(Long chatId, String name) {
        Optional<User> checkUser = userRepository.findById(chatId);

        if (checkUser.isEmpty()) {
            User user = new User();
            user.setChatId(chatId);
            user.setUserName(name);
            User save = userRepository.save(user);
            return true;
        }
        return false;
    }

    public void setMessageToEntity(final Long chatId, final String name, final Long messageId,final String message){

        Optional<User> user = userRepository.findById(chatId);

        user.ifPresentOrElse(
                (value)
                        -> value.setRepeat(Arrays.asList(new Repeat(messageId, message))),
                ()
                        -> {
                    User newUser = new User(chatId, name, Arrays.asList(new Repeat(messageId, message)));
                    userRepository.save(newUser);
                });

    }

    public String getAllMessages(final Long chatId, final String name){

        Optional<User> user = userRepository.findById(chatId);
        String message = null;

        if(user.isPresent()){
            if(user.get().getRepeat().isEmpty()){
                message = "You don't have any stored messages";
            } else{
                List<RepeatDto> repeatDtos = botMapper.map(user.get().getRepeat());
                message = repeatDtos
                        .stream()
                        .map(i -> i.getMessageId() + " " + i.getMessage())
                        .collect(Collectors.joining("\n"));
            }
        } else {
            message = "You don't have any stored messages";
            createNewUser(chatId, name);
        }

        return message;
    }



}