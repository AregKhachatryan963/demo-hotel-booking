package com.aca.demohotelbookingv1.service;

import com.aca.demohotelbookingv1.dto.roomDto.RoomRequestDto;
import com.aca.demohotelbookingv1.dto.roomDto.RoomResponseDto;
import com.aca.demohotelbookingv1.model.Hotel;
import com.aca.demohotelbookingv1.model.Room;
import com.aca.demohotelbookingv1.repositories.HotelRepository;
import com.aca.demohotelbookingv1.repositories.RoomRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
    RoomRepository roomRepository;
    HotelRepository hotelRepository;

    public RoomService(RoomRepository roomRepository, HotelRepository hotelRepository) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
    }

    public String enterRoom(RoomRequestDto roomRequest, Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId).get();
        Room room = new Room();
        room.setHotel(hotel);
        room.setRoomType(roomRequest.getRoomType());
        room.setRoomNumber(roomRequest.getRoomNumber());
        room.setPricePerDay(roomRequest.getPricePerDay());
        roomRepository.save(room);
        return "Room is saved.";
    }

    public String deleteRoomByHotelIdRoomId(Long hotelId, Long roomId) {
        if (roomRepository.existsByHotelIdRoomId(hotelId, roomId)) {
            roomRepository.deleteRoomByHotelIdRoomId(hotelId, roomId);
            return "Room is deleted";
        }
        return "Room doesn't exist";
    }
    public ResponseEntity<List<RoomResponseDto>> findAllRoomsByHotel(Long hotelId) {
        if (!hotelRepository.existsById(hotelId)) {
            throw new RuntimeException("Hotel doesn't exist.");
        }
        List<Room> allRooms = roomRepository.findAllByHotelId(hotelId);
        List<RoomResponseDto> roomResponseDtos = allRooms.stream().map(room ->
                {
                    RoomResponseDto roomResponseDto = new RoomResponseDto();
                    roomResponseDto.setRoomNumber(room.getRoomNumber());
                    roomResponseDto.setRoomType(room.getRoomType());
                    roomResponseDto.setId(room.getId());
                    roomResponseDto.setPricePerDay(room.getPricePerDay());
                    return roomResponseDto;
                }
        ).collect(Collectors.toList());
        return ResponseEntity.ok(roomResponseDtos);
    }

    public ResponseEntity<RoomResponseDto> findRoomByHotelIdRoomId(Long hotelId, Long roomId) {

        Room room = roomRepository.findRoomByHotelIdRoomId(hotelId, roomId);
        RoomResponseDto roomResponseDto = new RoomResponseDto();
        roomResponseDto.setRoomType(room.getRoomType());
        roomResponseDto.setRoomNumber(room.getRoomNumber());
        roomResponseDto.setId(room.getId());
        return ResponseEntity.ok(roomResponseDto);
    }

    public String updateRoom(Long hotelId, Long roomId, RoomRequestDto roomRequestDto) {
        if (roomRepository.existsByHotelIdRoomId(hotelId, roomId)) {
            Room room = roomRepository.findRoomByHotelIdRoomId(hotelId, roomId);
            room.setRoomType(roomRequestDto.getRoomType());
            room.setPricePerDay(roomRequestDto.getPricePerDay());
            roomRepository.save(room);
            return "Room is updated";
        }
        return "Room doesn't exist";
    }
}
