package com.aca.demohotelbookingv1.service;

import com.aca.demohotelbookingv1.auth.userDto.UserLoginRequestDto;
import com.aca.demohotelbookingv1.auth.userDto.UserLoginResponseDto;
import com.aca.demohotelbookingv1.auth.userDto.UserRegistrationRequestDto;
import com.aca.demohotelbookingv1.auth.userDto.UserRegistrationResponseDto;
import com.aca.demohotelbookingv1.auth.utils.JwtUtils;
import com.aca.demohotelbookingv1.exeption.InvalidUsernamePasswordException;
import com.aca.demohotelbookingv1.model.User;
import com.aca.demohotelbookingv1.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.aca.demohotelbookingv1.model.Role;

import java.util.Optional;

@Service
public class AuthService {
private final UserRepository userRepository;
private final BCryptPasswordEncoder bCryptPasswordEncoder;
private final JwtUtils jwtUtils;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtils = jwtUtils;
    }
    public String registerAdmin(){
        if(userRepository.existsByRole("ADMIN")){
            return "Get out, idiot.";
        }
        User superAdmin = new User();
        superAdmin.setEmail("admin@.com");
        superAdmin.setPassword(bCryptPasswordEncoder.encode("superAdmin"));
        superAdmin.setRole(Role.ADMIN);
        userRepository.save(superAdmin);
        return "Admin is saved";
    }
    public UserRegistrationResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto){
        User user = new User();
        user.setEmail(userRegistrationRequestDto.getEmail());
        user.setFirstName(userRegistrationRequestDto.getFirstName());
        user.setLastName(userRegistrationRequestDto.getLastName());
        user.setPassword(bCryptPasswordEncoder.encode(userRegistrationRequestDto.getPassword()));
        user.setRole(Role.USER);
        User savedUser = userRepository.save(user);

        UserRegistrationResponseDto userRegistrationResponseDto = new UserRegistrationResponseDto();
        userRegistrationResponseDto.setEmail(savedUser.getEmail());
        userRegistrationResponseDto.setFirstName(savedUser.getFirstName());
        userRegistrationResponseDto.setLastName(savedUser.getLastName());
        userRegistrationResponseDto.setId(savedUser.getId());
        return userRegistrationResponseDto;
    }
    public UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto) throws InvalidUsernamePasswordException {
        Optional<User> userOptional = userRepository.findByEmail(userLoginRequestDto.getEmail());
        if(userOptional.isEmpty()){
            throw new InvalidUsernamePasswordException("Invalid Username");
        }
        User user = userOptional.get();

        if(!bCryptPasswordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword())){
            throw new InvalidUsernamePasswordException("Invalid password");
        }
        UserLoginResponseDto userLoginResponseDto = new UserLoginResponseDto();
        userLoginResponseDto.setEmail(user.getEmail());
        userLoginResponseDto.setAccessToken(jwtUtils.createToken(user));
        return userLoginResponseDto;
    }
}
