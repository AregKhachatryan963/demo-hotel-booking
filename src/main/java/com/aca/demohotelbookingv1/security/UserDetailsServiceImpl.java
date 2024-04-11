package com.aca.demohotelbookingv1.security;

import com.aca.demohotelbookingv1.model.User;
import com.aca.demohotelbookingv1.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            throw new RuntimeException("User With Email doesn't exists");
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(new String []{user.getRole()})
                .build();
    }
}
