package com.aca.demohotelbookingv1.repositories;

import com.aca.demohotelbookingv1.model.Room;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {List<Room> findAllByHotelId(Long hotelId);

    @Query("select r from Room r inner join r.hotel h where h.id = :hotelId and r.id = :roomId")
    Room findRoomByHotelIdRoomId(Long hotelId, Long roomId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Room r WHERE r.hotel.id = :hotelId AND r.id = :roomId")
    void deleteRoomByHotelIdRoomId(Long hotelId, Long roomId);
}
