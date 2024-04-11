package com.aca.demohotelbookingv1.dto.roomDto;

import lombok.Data;

@Data
public class RoomResponseDto {
    private Long id;
    private String roomType;
    private Integer roomNumber;
    private boolean isBooked;
    private double pricePerDay;
}
