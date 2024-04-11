package com.aca.demohotelbookingv1.service;

import com.aca.demohotelbookingv1.model.DateStatus;
import com.aca.demohotelbookingv1.model.Room;
import com.aca.demohotelbookingv1.dto.dateStatusDto.DateStatusBookRequestDto;
import com.aca.demohotelbookingv1.dto.dateStatusDto.DateStatusResponseDto;
import com.aca.demohotelbookingv1.repositories.DateStatusRepository;
import com.aca.demohotelbookingv1.repositories.RoomRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DateStatusService {

DateStatusRepository dateStatusRepository;
    RoomRepository roomRepository;
    public DateStatusService(DateStatusRepository dateStatusRepository, RoomRepository roomRepository) {
        this.dateStatusRepository = dateStatusRepository;
        this.roomRepository = roomRepository;
    }
    public String insertRoomStatus(Long hotelId, Long roomId){
        for(int i = 1; i <= 5; i++ ){
            Room room = roomRepository.findRoomByHotelIdRoomId(hotelId, roomId);
            DateStatus dateStatus = new DateStatus();
            dateStatus.setDate(LocalDate.now().plusDays(i));
            dateStatus.setBooked(false);
            dateStatus.setRoom(room);
            dateStatusRepository.save(dateStatus);
        }
        return "Status is saved.";
    }
    public ResponseEntity<List<DateStatusResponseDto>> findAllStatusesByHotelIdRoomId(Long hotelId, Long roomId){
        List<DateStatus> allStatuses = dateStatusRepository.findAllByHotelIdRoomId(hotelId, roomId);
        List<DateStatusResponseDto> response = allStatuses.stream()
                .map(dateStatus ->
                {
                    DateStatusResponseDto responseDto = new DateStatusResponseDto();
                    responseDto.setId(dateStatus.getId());
                    responseDto.setDate(dateStatus.getDate());
                    responseDto.setBooked(dateStatus.isBooked());
                    return responseDto;
                }).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<DateStatusResponseDto> findByHotelIdRoomIdDateStatusId(Long hotelId, Long roomId, Long dateStatusId){
        DateStatus dateStatus = dateStatusRepository.findByHotelIdRoomIdDateStatusId(hotelId, roomId, dateStatusId);
        DateStatusResponseDto responseDto = new DateStatusResponseDto();
        responseDto.setId(dateStatus.getId());
        responseDto.setDate(dateStatus.getDate());
        responseDto.setBooked(dateStatus.isBooked());
        return ResponseEntity.ok(responseDto);
    }
    public String book(Long userId, Long hotelId, Long roomId,  Long dateStatusId, DateStatusBookRequestDto requestDto){
        if(dateStatusRepository.existsByHotelIdRoomIdDateStatusId(hotelId, roomId, dateStatusId)) {
            DateStatus dateStatus = dateStatusRepository.findByHotelIdRoomIdDateStatusId(hotelId, roomId, dateStatusId);
            if(dateStatus.isBooked()){
                return "Room is already booked by other customer.";
            }
            dateStatus.setBooked(true);
            dateStatus.setFirstName(requestDto.getFirstName());
            dateStatus.setLastName(requestDto.getLastName());
            dateStatus.setPassportId(requestDto.getPassportId());
            dateStatus.setUserId(userId);
            dateStatusRepository.save(dateStatus);
            return "Room is booked.";
        }
        return "Room is not booked, check data and try again";
    }
    public String unbook(Long userId, Long hotelId, Long roomId, Long dateStatusId){
        if(dateStatusRepository.existsByHotelIdRoomIdDateStatusId(hotelId, roomId, dateStatusId)) {
            DateStatus dateStatus = dateStatusRepository.findByHotelIdRoomIdDateStatusId(hotelId, roomId, dateStatusId);
            if(Objects.equals(dateStatus.getUserId(), userId)) {
                dateStatus.setBooked(false);
                dateStatus.setFirstName(null);
                dateStatus.setLastName(null);
                dateStatus.setPassportId(null);
                dateStatusRepository.save(dateStatus);
                return "Room is unbooked.";
            }
            return "Room is not unbooked, check data";
        }
        return "You can't unbook this reservation.";
    }
}
