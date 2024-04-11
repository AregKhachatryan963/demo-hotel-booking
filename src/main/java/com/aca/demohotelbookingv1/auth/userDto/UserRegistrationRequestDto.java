package com.aca.demohotelbookingv1.auth.userDto;

import lombok.Data;

@Data
public class UserRegistrationRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}