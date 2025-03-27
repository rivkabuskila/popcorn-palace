package com.att.tdp.popcorn_palace.repository;


import com.att.tdp.popcorn_palace.model.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingsRepository extends JpaRepository<Bookings, Long> {
    boolean existsByShowtimeIdAndSeatNumber(Long showtimeId, int seatNumber);
}
