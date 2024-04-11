package com.aca.demohotelbookingv1.controllers;

import com.aca.demohotelbookingv1.dto.roomDto.RoomRequestDto;
import com.aca.demohotelbookingv1.dto.roomDto.RoomResponseDto;
import com.aca.demohotelbookingv1.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/{userId}/hotels/{hotelId}/rooms")
public class RoomController {

    private RoomService roomService;


    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority(T(com.aca.demohotelbookingv1.model.Role).ADMIN) and @userAccessChecker.isUserValid(#userId)")
    public String enterRoom(@RequestBody RoomRequestDto roomDto, @PathVariable Long userId, @PathVariable Long hotelId) {
        return roomService.enterRoom(roomDto, hotelId);
    }

    @DeleteMapping("/{roomId}")
    @PreAuthorize("hasAuthority(T(com.aca.demohotelbookingv1.model.Role).ADMIN) and @userAccessChecker.isUserValid(#userId)")
    public String deleteRoomByHotelIdRoomId(@PathVariable Long userId, @PathVariable Long hotelId, @PathVariable Long roomId) {
        return roomService.deleteRoomByHotelIdRoomId(hotelId, roomId);
    }

    @GetMapping
    @PreAuthorize("permitAll() and @userAccessChecker.isUserValid(#userId)")
    public ResponseEntity<List<RoomResponseDto>> findAllRoomsByHotel(@PathVariable Long userId, @PathVariable Long hotelId) {
        return roomService.findAllRoomsByHotel(hotelId);
    }

    @GetMapping("/{roomId}")
    @PreAuthorize("permitAll() and @userAccessChecker.isUserValid(#userId)")
    public ResponseEntity<RoomResponseDto> findRoomByHotelIdRoomId(@PathVariable Long userId, @PathVariable Long hotelId, @PathVariable Long roomId) {

        return roomService.findRoomByHotelIdRoomId(hotelId, roomId);
    }
    @PutMapping("/{roomId}")
    @PreAuthorize("hasAuthority(T(com.aca.demohotelbookingv1.model.Role).ADMIN) and @userAccessChecker.isUserValid(#userId)")
    public String updateRoom(@PathVariable Long userId, @PathVariable Long hotelId, @PathVariable Long roomId, @RequestBody RoomRequestDto roomRequestDto){
        return roomService.updateRoom(hotelId, roomId, roomRequestDto);
    }
}
