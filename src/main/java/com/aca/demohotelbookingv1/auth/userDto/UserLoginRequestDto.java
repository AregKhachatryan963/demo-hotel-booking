package com.aca.demohotelbookingv1.auth.userDto;

import lombok.Data;

@Data
public class UserLoginRequestDto {
    private String email;
    private String password;
}
