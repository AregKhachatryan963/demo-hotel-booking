package com.aca.demohotelbookingv1.controllers;

import com.aca.demohotelbookingv1.model.Hotel;
import com.aca.demohotelbookingv1.service.HotelService;
import com.aca.demohotelbookingv1.dto.hotelDto.HotelDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("{userId}/hotels")
public class HotelController {
    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority(T(com.aca.demohotelbookingv1.model.Role).ADMIN) and @userAccessChecker.isUserValid(#userId)")
    public Hotel enterNewHotel(@PathVariable Long userId, @RequestBody HotelDto hotelDto) {
        return hotelService.enterNewHotel(hotelDto);
    }

    @GetMapping
    @PreAuthorize("permitAll() and @userAccessChecker.isUserValid(#userId)")
    public List<HotelDto> findAllHotels(@PathVariable Long userId) {
        return hotelService.findAllHotels();
    }

    @GetMapping("/{hotelId}")
    @PreAuthorize("permitAll() and @userAccessChecker.isUserValid(#userId)")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable Long userId, @PathVariable Long hotelId) {
        return ResponseEntity.ok(hotelService.findHotelById(hotelId)).getBody();
    }

    @PutMapping("/{hotelId}")
    @PreAuthorize("hasAuthority(T(com.aca.demohotelbookingv1.model.Role).ADMIN) and @userAccessChecker.isUserValid(#userId)")
    public String updateHotel(@RequestBody HotelDto hotelDto, @PathVariable Long userId, @PathVariable Long hotelId) {
        return hotelService.updateHotel(hotelId, hotelDto);
    }

    @DeleteMapping("/{hotelId}")
    @PreAuthorize("hasAuthority(T(com.aca.demohotelbookingv1.model.Role).ADMIN) and @userAccessChecker.isUserValid(#userId)")
    public String deleteHotel(@PathVariable Long userId, @PathVariable Long hotelId) {
        return hotelService.deleteById(hotelId);
    }
}
