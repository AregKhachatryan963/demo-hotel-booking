package com.aca.demohotelbookingv1.dto.dateStatusDto;

import lombok.Data;

@Data
public class DateStatusBookRequestDto {
    private boolean isBooked;
    private String firstName;
    private String lastName;
    private String passportId;
    private Long userId;
}
