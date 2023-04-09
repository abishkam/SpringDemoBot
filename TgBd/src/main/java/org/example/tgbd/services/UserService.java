package org.example.tgbd.services;

import org.example.tgbd.model.User;
import org.example.tgbd.model.UserRepository;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Boolean createNew(Long chatId, String name) {
        if (userRepository.findById(chatId).isEmpty()) {
            User user = new User();
            user.setChatId(chatId);
            user.setUserName(name);
            User save = userRepository.save(user);
            return true;
        }
        return false;
    }
}
