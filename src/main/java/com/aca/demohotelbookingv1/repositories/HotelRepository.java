package com.aca.demohotelbookingv1.repositories;

import com.aca.demohotelbookingv1.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long>{
    boolean existsByHotelName(String hotelName);

}
