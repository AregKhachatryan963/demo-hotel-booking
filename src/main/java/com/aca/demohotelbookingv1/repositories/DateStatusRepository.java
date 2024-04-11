package com.aca.demohotelbookingv1.repositories;

import com.aca.demohotelbookingv1.model.DateStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface DateStatusRepository extends JpaRepository<DateStatus, Long> {

    @Query("select count(*) > 0 from DateStatus d inner join d.room r inner join r.hotel h where h.id = :hotelId and r.id = :roomId and d.id = :dateStatusId")
    boolean existsByHotelIdRoomIdDateStatusId(Long hotelId, Long roomId, Long dateStatusId);

    @Query("select d from DateStatus d inner join d.room r inner join r.hotel h where h.id = :hotelId and r.id = :roomId")
    List<DateStatus> findAllByHotelIdRoomId(Long hotelId, Long roomId);

    @Query("select d from DateStatus d inner join d.room r inner join r.hotel h where h.id = :hotelId and r.id = :roomId and d.id = :dateStatusId")
    DateStatus findByHotelIdRoomIdDateStatusId(Long hotelId, Long roomId, Long dateStatusId);
}
