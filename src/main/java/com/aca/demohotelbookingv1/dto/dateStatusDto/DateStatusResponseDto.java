package com.aca.demohotelbookingv1.dto.dateStatusDto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DateStatusResponseDto {
    private Long id;
    private LocalDate date;
    private boolean isBooked;
}
