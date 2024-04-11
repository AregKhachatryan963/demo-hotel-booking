package com.aca.demohotelbookingv1.controllers;

import com.aca.demohotelbookingv1.dto.dateStatusDto.DateStatusBookRequestDto;
import com.aca.demohotelbookingv1.dto.dateStatusDto.DateStatusResponseDto;
import com.aca.demohotelbookingv1.service.DateStatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/{userId}/hotels/{hotelId}/rooms/{roomId}/date")
public class DateStatusController {

    DateStatusService dateStatusService;

    public DateStatusController(DateStatusService dateStatusService) {
        this.dateStatusService = dateStatusService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority(T(com.aca.demohotelbookingv1.model.Role).ADMIN) and @userAccessChecker.isUserValid(#userId)")
    public String insertRoomPerDays(@PathVariable Long userId, @PathVariable Long hotelId, @PathVariable Long roomId) {
        return dateStatusService.insertRoomStatus(hotelId, roomId);
    }

    @GetMapping
    @PreAuthorize("permitAll() and @userAccessChecker.isUserValid(#userId)")
    public ResponseEntity<List<DateStatusResponseDto>> findAllStatuses(@PathVariable Long userId, @PathVariable Long hotelId, @PathVariable Long roomId) {
        return dateStatusService.findAllStatusesByHotelIdRoomId(hotelId, roomId);
    }
    @GetMapping("/{dateStatusId}")
    @PreAuthorize("permitAll() and @userAccessChecker.isUserValid(#userId)")
    public ResponseEntity<DateStatusResponseDto> findByHotelIdRoomIdDateStatusId(@PathVariable Long userId, @PathVariable Long hotelId, @PathVariable Long roomId, @PathVariable Long dateStatusId){
        return dateStatusService.findByHotelIdRoomIdDateStatusId(hotelId, roomId, dateStatusId);
    }

    @PutMapping("/{dateStatusId}/book")
    @PreAuthorize("hasAuthority(T(com.aca.demohotelbookingv1.model.Role).USER) and @userAccessChecker.isUserValid(#userId)")
    public String book(@PathVariable Long userId, @PathVariable Long hotelId, @PathVariable Long roomId, @PathVariable Long dateStatusId, @RequestBody DateStatusBookRequestDto dateStatusBookRequestDto) {
        return dateStatusService.book(userId, hotelId, roomId, dateStatusId, dateStatusBookRequestDto);
    }

    @PutMapping("/{dateStatusId}/unbook")
    @PreAuthorize("hasAuthority(T(com.aca.demohotelbookingv1.model.Role).USER) and @userAccessChecker.isUserValid(#userId)")
    public String unbook(@PathVariable Long userId, @PathVariable Long hotelId, @PathVariable Long roomId, @PathVariable Long dateStatusId, @RequestBody DateStatusBookRequestDto dateStatusBookRequestDto) {
        return dateStatusService.unbook(userId, hotelId, roomId, dateStatusId);
    }
}