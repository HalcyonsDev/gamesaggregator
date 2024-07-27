package ru.halcyon.telegrambot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.halcyon.telegrambot.model.User;
import ru.halcyon.telegrambot.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void registerUser(String username, long chatId) {
        if (!userRepository.existsByChatId(chatId)) {
            User user = new User(username, chatId);
            userRepository.save(user);
        }
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
