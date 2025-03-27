package com.att.tdp.popcorn_palace.model;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
//import java.time.Instant;


@Entity
@Table(name = "Showtime")
public class Showtime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "price")
    private double price;
    @Column(name = "movie_id")
    private Long movieId;
    @Column(name = "theater")
    private String theater;
    @Column(name = "start_time")
    private ZonedDateTime startTime;
    @Column(name = "end_time")
    private ZonedDateTime endTime;


    public Showtime(Long movieID, String theater, ZonedDateTime   startTime, ZonedDateTime  endTime, double price) {
        this.movieId = movieID;
        this.price = price;
        this.theater = theater;
        this.startTime = startTime;
        this.endTime = endTime;

    }
    public Showtime() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMovieId() {
        return movieId;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public String getTheater() {
        return theater;
    }

    public double getPrice() {
        return price;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setMovie(Long movieID) {
        this.movieId = movieID;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public void setTheater(String theater) {
        this.theater = theater;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
