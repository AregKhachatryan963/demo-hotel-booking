package com.aca.demohotelbookingv1.dto.roomDto;

import lombok.Data;

@Data
public class RoomRequestDto {
    private String roomType;
    private Integer roomNumber;
    private double pricePerDay;
}
