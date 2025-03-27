package com.att.tdp.popcorn_palace.controller;
import com.att.tdp.popcorn_palace.model.Movie;
import com.att.tdp.popcorn_palace.model.Theater;
import com.att.tdp.popcorn_palace.repository.MovieRepository;
import com.att.tdp.popcorn_palace.repository.ShowtimeRepository;
import com.att.tdp.popcorn_palace.exception.ResourceNotFoundException;
import com.att.tdp.popcorn_palace.model.Showtime;
import com.att.tdp.popcorn_palace.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/showtimes")
public class ShowtimeController {
    @Autowired
    private ShowtimeRepository ShowtimeRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private TheaterRepository theaterRepository;

    private void checkValidity(Showtime showtime ){
        Long movieID = showtime.getMovieId();
        if (movieID == null) {
            throw new ResourceNotFoundException("movieID must not be null");
        }
        if (!movieRepository.existsById(movieID)) {
            throw new ResourceNotFoundException("movie not found with id: " + movieID);
        }
        if (showtime.getStartTime() == null) {
            throw new ResourceNotFoundException("StartTime is required and cannot be empty");
        }

        if (showtime.getEndTime() == null ) {
            throw new ResourceNotFoundException("EndTime is required and cannot be empty");
        }
        if (showtime.getEndTime().isBefore(showtime.getStartTime() )) {
            throw new ResourceNotFoundException("End time must be after start time");
        }
        Optional<Theater> optionalTheater = theaterRepository.findById(showtime.getTheater());
        if (!optionalTheater.isPresent()) {
            throw new ResourceNotFoundException("Theater not found!");
        }
    }

    private void checkOverlappingS(Showtime showtime){
        List<Showtime> overlappingShowtimes = ShowtimeRepository.findOverlappingShowtimes(
                showtime.getId(),
                showtime.getTheater(),
                showtime.getStartTime(),
                showtime.getEndTime()
        );

        if (!overlappingShowtimes.isEmpty()) {
            throw new ResourceNotFoundException("There is already a showtime scheduled for this theater at the given time.");
        }

    }
    @GetMapping("/{id}")
    public ResponseEntity<Showtime> getShoetimesById(@PathVariable Long id) {
        Showtime showtime = ShowtimeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("showtime not exist with id :" + id));
        return ResponseEntity.ok(showtime);
    }

    @PostMapping("")
    public Showtime createShowtimes(@RequestBody Showtime showtime) {
        checkValidity(showtime);
        checkOverlappingS(showtime);
        return ShowtimeRepository.save(showtime);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Showtime> updateShowtimes(@PathVariable Long id, @RequestBody Showtime showtimeDetails){
        Showtime showtime  = ShowtimeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("showtime not exist with id :" + id));
        checkValidity(showtime);
        checkOverlappingS(showtime);
        showtime.setTheater(showtimeDetails.getTheater());
        showtime.setMovie(showtimeDetails.getMovieId());
        showtime.setEndTime(showtimeDetails.getEndTime());
        showtime.setPrice(showtimeDetails.getPrice());
        showtime.setStartTime(showtimeDetails.getStartTime());
        ShowtimeRepository.save(showtime);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteShowTimes(@PathVariable Long id){
        Showtime showtime = ShowtimeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("showtime not exist with id :" + id));

        ShowtimeRepository.delete(showtime);
        return ResponseEntity.ok().build();

    }
}