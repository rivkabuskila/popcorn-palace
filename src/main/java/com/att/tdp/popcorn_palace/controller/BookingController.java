package com.att.tdp.popcorn_palace.controller;

import com.att.tdp.popcorn_palace.exception.ResourceNotFoundException;
import com.att.tdp.popcorn_palace.model.BookingResponse;
import com.att.tdp.popcorn_palace.model.Bookings;
import com.att.tdp.popcorn_palace.model.Showtime;
import com.att.tdp.popcorn_palace.model.Theater;
import com.att.tdp.popcorn_palace.repository.BookingsRepository;
import com.att.tdp.popcorn_palace.repository.ShowtimeRepository;
import com.att.tdp.popcorn_palace.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.*;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingsRepository bookingsRepository;
    @Autowired
    private ShowtimeRepository showtimeRepository;
    @Autowired
    private TheaterRepository theaterRepository;

    @PostMapping("")
    public ResponseEntity<?> createBooking(@RequestBody Bookings bookings) {
        Long showtimeId = bookings.getShowtimeId();

        if (!showtimeRepository.existsById(showtimeId)) {
            throw new ResourceNotFoundException("Showtime not exist with id :" + showtimeId);
        }
        Showtime showtime = showtimeRepository.findById(showtimeId).get();
        ZonedDateTime currentTime = ZonedDateTime.now();
        if (showtime.getStartTime().isBefore(currentTime)) {
            throw new ResourceNotFoundException("Showtime with id " + showtimeId + " has already passed.");
        }
        Optional<Theater> optionalTheater = theaterRepository.findById(showtime.getTheater());
        if (!optionalTheater.isPresent()) {
            throw new ResourceNotFoundException("Theater not found!");
        }
        Theater theater = optionalTheater.get();
        if (theater.getTotalSeats() < bookings.getSeatNumber() || bookings.getSeatNumber()  < 1){
            throw new ResourceNotFoundException("invalid seat number");
        }
        if (bookingsRepository.existsByShowtimeIdAndSeatNumber(bookings.getShowtimeId(), bookings.getSeatNumber())) {
            throw new ResourceNotFoundException("Seat already booked. Please choose another seat");
        }
        Bookings savedBooking = bookingsRepository.save(bookings);
        Map<String, String> response = new HashMap<>();
        response.put("bookingId", savedBooking.getUserId().toString());

        return ResponseEntity.ok().body(new BookingResponse(savedBooking.getBookingId()));
    }


}
