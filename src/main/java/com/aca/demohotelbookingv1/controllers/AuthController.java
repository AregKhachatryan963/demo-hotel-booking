package com.aca.demohotelbookingv1.controllers;

import com.aca.demohotelbookingv1.auth.userDto.UserLoginRequestDto;
import com.aca.demohotelbookingv1.auth.userDto.UserLoginResponseDto;
import com.aca.demohotelbookingv1.auth.userDto.UserRegistrationRequestDto;
import com.aca.demohotelbookingv1.auth.userDto.UserRegistrationResponseDto;
import com.aca.demohotelbookingv1.exeption.InvalidUsernamePasswordException;
import com.aca.demohotelbookingv1.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/register_admin")
    public String registerAdmin(){
        return authService.registerAdmin();
    }
    @PostMapping("/register")
    public UserRegistrationResponseDto register(@RequestBody UserRegistrationRequestDto userRegistrationRequestDto){
        return authService.register(userRegistrationRequestDto);
    }
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto userLoginRequestDto){
        try{
            return ResponseEntity.ok(authService.login(userLoginRequestDto));
        }
        catch (InvalidUsernamePasswordException exception){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
