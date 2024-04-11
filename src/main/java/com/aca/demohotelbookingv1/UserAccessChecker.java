package com.aca.demohotelbookingv1;

import com.aca.demohotelbookingv1.repositories.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserAccessChecker {
    private final UserRepository userRepository;

    public UserAccessChecker(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isUserValid(Long userId) {
        return userRepository.existsById(userId);
    }
}
