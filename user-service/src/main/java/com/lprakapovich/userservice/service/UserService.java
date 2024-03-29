package com.lprakapovich.userservice.service;

import com.lprakapovich.userservice.domain.User;
import com.lprakapovich.userservice.exception.DuplicatedUsernameException;
import com.lprakapovich.userservice.exception.UserNotFoundException;
import com.lprakapovich.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Long createUser(User user) {
        checkUniqueness(user.getUsername());
        return userRepository.save(user).getId();
    }

    public User getByUsername(String username) {
        return userRepository.getByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    void checkUniqueness(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new DuplicatedUsernameException();
        }
    }
}
