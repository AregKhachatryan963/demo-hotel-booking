package com.aca.demohotelbookingv1.service;

import com.aca.demohotelbookingv1.dto.hotelDto.HotelDto;
import com.aca.demohotelbookingv1.model.Hotel;
import com.aca.demohotelbookingv1.repositories.HotelRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelService {

private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public Hotel enterNewHotel(HotelDto hotelDto) {
        if (hotelRepository.existsByHotelName(hotelDto.getHotelName())) {
            throw new RuntimeException("hotel already exists");
        }
        Hotel hotel = new Hotel();
        hotel.setHotelName(hotelDto.getHotelName());
        hotel.setRating(hotelDto.getRating());
        hotel.setAddress(hotelDto.getAddress());
        return hotelRepository.save(hotel);
    }

    public List<HotelDto> findAllHotels() {
        List<Hotel> all = hotelRepository.findAll();
        List<HotelDto> hotelDtos = all.stream().map(hotel -> {
            HotelDto hoteldto = new HotelDto();
            hoteldto.setHotelName(hotel.getHotelName());
            hoteldto.setAddress(hotel.getAddress());
            hoteldto.setRating(hotel.getRating());
            return hoteldto;
        }).collect(Collectors.toList());
        return hotelDtos;
    }

    public ResponseEntity<HotelDto> findHotelById(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(RuntimeException::new);
        HotelDto hotelDto = new HotelDto();
        hotelDto.setHotelName(hotel.getHotelName());
        hotelDto.setAddress(hotel.getAddress());
        hotelDto.setRating(hotel.getRating());
        return ResponseEntity.ok(hotelDto);
    }

    //    public ResponseEntity<Hotel> getHotelByName(String hotelName){
//        if (!hotelRepository.existsByHotelName(hotelName)) {
//            throw new RuntimeException("Wrong hotel name");
//        }
//        return ResponseEntity.ok(hotelRepository.findByHotelName(hotelName));
//    }
    public String updateHotel(Long hotelId, HotelDto hotelDto) {

        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(RuntimeException::new);
        if (hotelDto.getRating() != null) {
            hotel.setRating(hotelDto.getRating());
        }
        if (hotelDto.getHotelName() != null) {
            hotel.setHotelName(hotelDto.getHotelName());
        }
        if (hotelDto.getAddress() != null) {
            hotel.setAddress(hotelDto.getAddress());
        }
        hotelRepository.save(hotel);
        return "Hotel is updated.";
    }

    public String deleteById(Long hotelId) {
        if (hotelRepository.existsById(hotelId)) {
            hotelRepository.deleteById(hotelId);
            return "Hotel is deleted";
        }
        return "Hotel with this ID doesn't exist.";
    }
}
