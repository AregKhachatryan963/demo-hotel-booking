package com.aca.demohotelbookingv1.auth.userDto;

import lombok.Data;

@Data
public class UserLoginResponseDto {
    private String email;
    private String accessToken;
}
