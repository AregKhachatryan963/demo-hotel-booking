package com.aca.demohotelbookingv1.auth.userDto;

import lombok.Data;

@Data
public class UserRegistrationResponseDto {

    private String firstName;
    private String lastName;
    private String email;
    private Long id;
}
