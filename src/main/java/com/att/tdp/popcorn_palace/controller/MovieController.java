package com.att.tdp.popcorn_palace.controller;
import com.att.tdp.popcorn_palace.repository.MovieRepository;
import com.att.tdp.popcorn_palace.exception.ResourceNotFoundException;
import com.att.tdp.popcorn_palace.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/all")
    public List<Movie> getAllMovies(){
        return movieRepository.findAll();
    }

    private void checkValidity(Movie movie){
        if (movie.getReleaseYear() < 1900 || movie.getReleaseYear() > 2100) {
            throw new ResourceNotFoundException("Year must be between 1900 and 2100");
        }
        if (movie.getTitle() == null || movie.getTitle().isEmpty() || movie.getTitle().trim().isEmpty()) {
            throw new ResourceNotFoundException("Title is required and cannot be empty");
        }
        if (movie.getDuration() <= 0) {
            throw new ResourceNotFoundException("Duration must be greater than 0");
        }
        if (movie.getRating() < 0 || movie.getRating() > 10) {
            throw new ResourceNotFoundException("Rating must be between 0 to 10");
        }
    }
    @PostMapping("")
    public Movie createMovies(@RequestBody Movie movie) {
        checkValidity(movie);
        return movieRepository.save(movie);
    }


    @PostMapping("/update/{title}")
    public ResponseEntity<Void> updateMovie(@PathVariable String title, @RequestBody Movie movieDetails){
        Movie movie = movieRepository.findByTitle(title)
                .orElseThrow(() -> new ResourceNotFoundException("movie not exist with title :" + title));
        checkValidity(movieDetails);
        movie.setTitle(movieDetails.getTitle());
        movie.setGenre(movieDetails.getGenre());
        movie.setDuration(movieDetails.getDuration());
        movie.setRating(movieDetails.getRating());
        movie.setReleaseYear(movieDetails.getReleaseYear());

        movieRepository.save(movie);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{title}")
    public ResponseEntity<Void> deleteMovie(@PathVariable String title) {
        Movie movie = movieRepository.findByTitle(title)
                .orElseThrow(() -> new ResourceNotFoundException("movie not exist with title :" + title));

        movieRepository.delete(movie);
        return ResponseEntity.ok().build();
    }

}




